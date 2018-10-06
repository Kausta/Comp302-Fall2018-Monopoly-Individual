# Monopoly Individual Project

Prepared by Caner Korkmaz (ckorkmaz16@ku.edu.tr, 60047)
Individual Monopoly Project form Comp 320

Details
- P2P Distributed MVVM based Monopoly Game
- There is an initial peer creating game, but the rest can join to any peer
- Application is structured as an MVVM application
    - Views are Swing Views in package `com.canerkorkmaz.monopoly.view`
    - ViewModels (in`com.canerkorkmaz.monopoly.viewmodel`) communicate with views using custom made Events and UIEvents (events run in a different background thread, ui events run in swing thread)
    - Models contain both local models and models synced through the peer network
    - Repositories are in between ViewModels and Models
- Uses a simple dependency injection system made by myself (in `com.canerkorkmaz.monopoly.lib.di`), can handle singleton dependencies, cyclic singleton dependencies and normal (factory) dependencies, however not cyclic normal dependencies (which doesn't make sense)

## License


Copyright [2018] [Caner Korkmaz]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this repository except in compliance with the License.
You may obtain a copy of the License at either

- http://www.apache.org/licenses/LICENSE-2.0
- [LICENSE](./LICENSE)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
