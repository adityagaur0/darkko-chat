# darkko

Chat application

## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://docs.flutter.dev/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://docs.flutter.dev/cookbook)

For help getting started with Flutter development, view the
[online documentation](https://docs.flutter.dev/), which offers tutorials,
samples, guidance on mobile development, and a full API reference.

| Character | Name                       | Encoded Value | Problem in URLs                                               |                              |
| --------- | -------------------------- | ------------- | ------------------------------------------------------------- | ---------------------------- |
| `#`       | Fragment identifier        | `%23`         | Cuts off the rest of the URL (browser treats it as an anchor) |                              |
| `/`       | Path separator             | `%2F`         | Breaks the route (`/` = new path segment)                     |                              |
| `?`       | Query string indicator     | `%3F`         | Treated as start of query params                              |                              |
| `&`       | Query param separator      | `%26`         | Breaks param structure                                        |                              |
| `=`       | Query param assignment     | `%3D`         | Confuses key=value structure                                  |                              |
| `%`       | Percent-encoding marker    | `%25`         | Must be encoded itself or breaks URL parsing                  |                              |
| `+`       | Space in query strings     | `%2B`         | Misinterpreted as space or symbol                             |                              |
| `:`       | Scheme separator (`http:`) | `%3A`         | Conflicts with URL scheme or port                             |                              |
| `\`       | Backslash                  | `%5C`         | Illegal in most URLs                                          |                              |
| `"`       | Double quote               | `%22`         | Can cause malformed requests                                  |                              |
| `'`       | Single quote               | `%27`         | Can break strings or SQL queries if not handled               |                              |
| `<` / `>` | HTML tags                  | `%3C` / `%3E` | Break HTML rendering / XSS risks                              |                              |
| `{` / `}` | JSON braces                | `%7B` / `%7D` | Not safe in URLs or headers                                   |                              |
| \`        | \`                         | Pipe          | `%7C`                                                         | Breaks shell, script parsing |
| `^`       | Caret                      | `%5E`         | Rare but problematic in CLI                                   |                              |
| `[` / `]` | Square brackets            | `%5B` / `%5D` | Used in IPv6, can confuse parsers                             |                              |
| `~`       | Tilde                      | `%7E`         | Sometimes encoded unnecessarily                               |                              |

