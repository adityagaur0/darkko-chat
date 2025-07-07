#!/bin/sh

# ===============================
# GitHub PR Creator Script
# ===============================
# Equivalent to GitLab MR script
# Requirements:
#   - gh (GitHub CLI): https://cli.github.com
#   - jq
# Usage:
#   ./create_github_pr.sh [-s source_branch] [-t target_branch] [-n title] [-d (draft)] [-r remote]
# ===============================

set -e

# Default values
SOURCE_BRANCH=$(git branch --show-current)
TARGET_BRANCH="main"
REMOTE="origin"
TITLE="Working on it"
DRAFT=false

while getopts "s:t:n:dr:h" opt; do
  case "${opt}" in
    s) SOURCE_BRANCH="$OPTARG" ;;
    t) TARGET_BRANCH="$OPTARG" ;;
    n) TITLE="$OPTARG" ;;
    r) REMOTE="$OPTARG" ;;
    d) DRAFT=true ;;
    h)
      echo "Usage: $0 [-s source_branch] [-t target_branch] [-n title] [-d (draft)] [-r remote]"
      exit 0
      ;;
  esac
done

# Ensure dependencies
command -v jq >/dev/null 2>&1 || { echo >&2 "jq is required but not installed."; exit 1; }
command -v gh >/dev/null 2>&1 || { echo >&2 "gh CLI is required but not installed."; exit 1; }

# Check login
if ! gh auth status >/dev/null 2>&1; then
  echo "You must be authenticated. Run: gh auth login"
  exit 1
fi

# Push current branch
if ! git ls-remote --exit-code --heads "$REMOTE" "$SOURCE_BRANCH" >/dev/null; then
  echo "Pushing branch $SOURCE_BRANCH to $REMOTE"
  git push --set-upstream "$REMOTE" "$SOURCE_BRANCH"
fi

# Prepare commit messages
COMMIT_MSGS=$(git log --no-merges --pretty=format:"#### %s (%h) ####%n%b" "$REMOTE/$TARGET_BRANCH..$REMOTE/$SOURCE_BRANCH")

# Compute line changes
DIFF_SUMMARY=$(git diff -w --stat HEAD "$REMOTE/$TARGET_BRANCH" -- \
  ':(exclude)*.json' \
  ':(exclude)*.g.dart' \
  ':(exclude)*.mocks.dart' \
  ':(exclude)**/mocks.dart' \
  ':(exclude)*.xml' \
  ':(exclude)*.svg' \
  ':(exclude)pubspec.lock')

INSERTIONS=$(echo "$DIFF_SUMMARY" | sed -n 's/.* \([0-9]*\) insertions.*/\1/p')
DELETIONS=$(echo "$DIFF_SUMMARY" | sed -n 's/.* \([0-9]*\) deletions.*/\1/p')

INSERTIONS=${INSERTIONS:-0}
DELETIONS=${DELETIONS:-0}
LINES_CHANGED=$((INSERTIONS + DELETIONS))

# Determine label size
LABEL="Size: XXL"
LABELS=("Size: XS" "Size: S" "Size: M" "Size: L" "Size: XL" "Size: XXL")
RANGES=(10 20 60 120 200)
for i in "${!RANGES[@]}"; do
  if [ "$LINES_CHANGED" -lt "${RANGES[$i]}" ]; then
    LABEL="${LABELS[$i]}"
    break
  fi

# Create PR body
BODY_FILE=$(mktemp)
echo "### Commits ###\n$COMMIT_MSGS" > "$BODY_FILE"

cat <<EOT >> "$BODY_FILE"

---

### Quality Checks
- [ ] Visual verified
- [ ] Orientation / Theme / Locale checks
- [ ] Tested on Android & iOS
- [ ] Feature flag used
- [ ] Crash-free, no warnings
- [ ] Flow tested end-to-end

---
Lines changed: $LINES_CHANGED ($INSERTIONS insertions / $DELETIONS deletions)
EOT

# Check if PR already exists
if gh pr view "$SOURCE_BRANCH" >/dev/null 2>&1; then
  echo "Updating existing PR for branch $SOURCE_BRANCH"
  gh pr edit "$SOURCE_BRANCH" --title "$TITLE" --body-file "$BODY_FILE"
else
  echo "Creating new PR from $SOURCE_BRANCH to $TARGET_BRANCH"
  if [ "$DRAFT" = true ]; then
    gh pr create --title "Draft: $TITLE" --body-file "$BODY_FILE" --base "$TARGET_BRANCH" --head "$SOURCE_BRANCH"
  else
    gh pr create --title "$TITLE" --body-file "$BODY_FILE" --base "$TARGET_BRANCH" --head "$SOURCE_BRANCH"
  fi
fi

# Add label
echo "Applying label: $LABEL"
gh pr edit "$SOURCE_BRANCH" --add-label "$LABEL"

rm "$BODY_FILE"
echo "Done."
