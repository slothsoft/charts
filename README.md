# Charts


[![Build Status](https://travis-ci.org/slothsoft/charts.svg?branch=master)](https://travis-ci.org/slothsoft/charts)

- **Author:** [Stef Schulz](mailto:s.schulz@slothsoft.de)
- **Repository:** <https://github.com/slothsoft/charts>
- **Open Issues:** <https://github.com/slothsoft/charts/issues>
- **Wiki:** <https://github.com/slothsoft/charts/wiki>
- **JavaDoc:** [slothsoft.github.io](https://slothsoft.github.io/charts)
- **Test Reports:** [Executed Tests](https://slothsoft.github.io/charts/tests), [Code Coverage](https://slothsoft.github.io/charts/coverage)

A framework for creating charts. For my musings before even starting to code see the [Preliminary Considerations](https://github.com/slothsoft/charts/wiki/Preliminary-Considerations).

## Getting Started

### Prerequisites

You need at least **Java 1.8** or above to use this library. You can use Maven as a build tool, but Gradle or using plain JARs should work as well.

The [.travis.yml](.travis.yml) shows which JDK this library is build and tested against.

### Installing

TODO: This library will be in Maven Central.


### Using the Framework

Examples for how to use this framework are located [here](examples/src/main/java/).

##  Versions


| Version       | Changes       |
| ------------- | ------------- |
| [Future](https://github.com/slothsoft/charts/milestone/2) |  |
| [0.1.0](https://github.com/slothsoft/charts/milestone/1?closed=1) | first draft of the API |


##  Features
    
If something is missing, request it via [a new issue](https://github.com/slothsoft/charts/issues/new).

### Chart

 - **JavaDoc:** [Chart](https://slothsoft.github.io/charts/de/slothsoft/charts/Chart)

All charts have these features:
	
 - hook `RefreshListener`s that get notified over changes
 - configure `Border` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/common/Border))
     - change general display information (spaces on all four sides)
 - configure `Title` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/common/Title))
     - change general display information (displayed text, color, font, size, position, text alignment)
 - change general display information (background color)
 
 
### LineChart

<img align="right" src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/line-chart-structure.png">

 - **JavaDoc:** [LineChart](https://slothsoft.github.io/charts/de/slothsoft/charts/linechart/LineChart)


Line charts have these features:

 - all the features of `Chart`
 - manage lines 
 - configure `XAxis` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/linechart/XAxis)) and `YAxis` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/linechart/YAxis))
     - change general display information (tick size and step length, arrow size, position)
     
<br clear="right"/>



## License

This project is licensed under the MIT License - see the [MIT license](LICENSE) for details.
