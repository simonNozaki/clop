# clop
Tiny Lisp interpreter by Clojure.

## Usage

### Prerequisite

- Java
  - 11 or over
- Leiningen
  - 2.9.8 or over

### Run REPL mode
Clop provides REPL mode to run tiny Lisp. After building a standalone uberjar by leiningen, run it:

```bash
# Compile and create a standalone uberjar
lein uberjar

cd target

# Run a jar and boot REPL
java -jar clop-0.1.0-SNAPSHOT-standalone.jar
```

### Functions, Syntax
Clop has only integer and sequence(vector, list) types and related functions. 

Basic macros:
- `quote` ... Return unevaluated form by list.
- `if`
- `begin` ... Start a series of expressions of sequential processes.
- `define`
- `lambda` ... Return a lambda function. This can be a body of defined function combined with `define`.
- `set!`

Built-in functions:
- `vector` ... Return a new vector.
- `first`
- `second`
- `last`
- `rest`

### Sample programs
```bash
$ java -jar clop-0.1.0-SNAPSHOT-standalone.jar
Clop 0.1.0
-> (define average (lambda (m n) (/ (+ m n) 2)))
:average
-> (average 3 7)
5
-> (begin (define r 3) (* 3.141592653 (* r r)))
28.27433466911316
```

## License

Copyright © 2023 Simon Nozaki

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
