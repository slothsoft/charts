# Charts


[![Build Status](https://travis-ci.org/slothsoft/charts.svg?branch=master)](https://travis-ci.org/slothsoft/charts) [![Maven Central](https://img.shields.io/maven-central/v/de.slothsoft.charts/charts.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22de.slothsoft.charts%22%20AND%20a:%22charts%22)

- **Author:** [Stef Schulz](mailto:s.schulz@slothsoft.de)
- **Repository:** <https://github.com/slothsoft/charts>
- **Open Issues:** <https://github.com/slothsoft/charts/issues>
- **Wiki:** <https://github.com/slothsoft/charts/wiki>
- **Developer Resources:** [JavaDoc](https://slothsoft.github.io/charts), [Executed Tests](https://slothsoft.github.io/charts/tests), [Code Coverage](https://slothsoft.github.io/charts/coverage)

A framework for creating charts. For my musings before even starting to code see the [Preliminary Considerations](https://github.com/slothsoft/charts/wiki/Preliminary-Considerations).

**Content:**

 - [Getting Started](#getting-started)
     - [Prerequisites](#prerequisites)
     - [Installing](#installing)
     - [Using the Framework](#using-the-framework)
 - [Versions](#versions)
 - [Features](#features)
     - [Chart](#chart)
     - [LineChart](#linechart)
     - [PieChart](#piechart)
 - [License](#license)



## Getting Started

### Prerequisites

You need at least **Java 1.8** or above to use this library. You can use Maven as a build tool, but Gradle or using plain JARs should work as well.

The [.travis.yml](.travis.yml) shows which JDK this library is build and tested against.

### Installing

This library is in Maven Central, so you can easily add it like this:

```xml
<dependency>
	<groupId>de.slothsoft.charts</groupId>
	<artifactId>charts</artifactId>
	<version>0.2.0</version>
</dependency>

<dependency>
	<groupId>de.slothsoft.charts</groupId>
	<artifactId>swt-charts</artifactId>
	<version>0.2.0</version>
</dependency>

<dependency>
	<groupId>de.slothsoft.charts</groupId>
	<artifactId>swing-charts</artifactId>
	<version>0.2.0</version>
</dependency>
```

For other build tools and the JAR take a look at [Maven Central](https://search.maven.org/artifact/de.slothsoft.charts/charts/) or the [MVN Repository](https://mvnrepository.com/artifact/de.slothsoft.charts/charts).


### Using the Framework

Examples for how to use this framework are located [here](examples/src/main/java/).

You usually start by creating the chart you wish to display.

**LineChart:**

```java
LineChart chart = new LineChart();
chart.addLine(new DataPointLine(-4, -2, -1, -0.5, 0));
chart.addLine(new FunctionLine(x -> Math.cos(x)).color(0xFF00FFFF));
```

**PieChart:**

```java
PieChart chart = new PieChart();
chart.addSlices(1, 3, 5, 15);
```

And then you need to set these charts as the model for your GUI framework.

**SWT:**

```java
ChartControl chartControl = new ChartControl(shell, SWT.BORDER);
chartControl.setModel(chart);
```

**Swing:**

```java
ChartControl chartControl = new ChartControl(chart);
```

There are more extensive examples [here](examples/src/main/java/), for instance:

 - **Chart types**
    - [LineChart](examples/src/main/java/linechart)
    - [PieChart](examples/src/main/java/piechart)
 - **GUI Frameworks**
    - [Swing](examples/src/main/java/swing)
    - [SWT](examples/src/main/java/swt)
 - **Miscellaneous**
    - [SVG](examples/src/main/java/other/SvgExample.java) (using [Apache Batik](https://xmlgraphics.apache.org/batik/))



##  Versions


| Version       | Changes       |
| ------------- | ------------- |
| [Future](https://github.com/slothsoft/charts/milestone/2) |  |
| [0.2.0](https://github.com/slothsoft/charts/milestone/3?closed=1) | Swing GUI & bar chart |
| [0.1.0](https://github.com/slothsoft/charts/milestone/1?closed=1) | first draft of the API |



##  Features
    
If something is missing, request it via [a new issue](https://github.com/slothsoft/charts/issues/new).

To see examples of the charts in action, [check the wiki page](https://github.com/slothsoft/charts/wiki/Chart-Examples). This page contains an image for each chart and GUI framework.

For the features of the GUI controls see [this wiki page](https://github.com/slothsoft/charts/wiki/GUI-Controls). 


### Chart

 - **JavaDoc:** [Chart](https://slothsoft.github.io/charts/de/slothsoft/charts/Chart)

All charts have these features:
	
 - hook `RefreshListener`s that get notified over changes
 - configure `Border` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/common/Border))
     - change general display information (spaces on all four sides)
 - configure `Title` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/common/Title))
     - change general display information (displayed text, color, font, size, position, text alignment)
 - change general display information (background color)
 
 

<img align="right" src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/line-chart-structure.png">

### LineChart

 - **JavaDoc:** [LineChart](https://slothsoft.github.io/charts/de/slothsoft/charts/linechart/LineChart)


Line charts have these features:

 - all the features of `Chart`
 - manage lines (add, remove)
     - line implementation for functions and for concrete data points
     - change general display information (color)
 - configure `XAxis` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/linechart/XAxis)) and `YAxis` ([JavaDoc](https://slothsoft.github.io/charts/de/slothsoft/charts/linechart/YAxis))
     - change general display information (tick size and step length, arrow size, position)
     
<br clear="right"/>



<img align="right" src="https://raw.githubusercontent.com/wiki/slothsoft/charts/images/pie-chart-structure.png">

### PieChart

 - **JavaDoc:** [PieChart](https://slothsoft.github.io/charts/de/slothsoft/charts/piechart/PieChart)


Pie charts have these features:

 - all the features of `Chart`
 - manage the slices of the chart (add, remove)
     - change general display information (percentage value, color)
 - change general display information (start angle, pie color, pie border)
     
<br clear="right"/>



## License

This project is licensed under the MIT License - see the [MIT license](LICENSE) for details.
