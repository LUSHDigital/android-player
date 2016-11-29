# Lush Player Library

## Setup

* The `initialise` method on `LushContent.class` must be called in your overridden `Application` class.

## Manager Classes

* Provide data for the caller from the API
* Decides whether to load the data from disk or the network
* Wraps the API and in doing so prevents API calls from other places such as views

## Sorter Classes

* Convenience class that allows multiple ways to sort a data set