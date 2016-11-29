# Lush Player Library

## Setup

* The `initialise` method on `LushContent.class` must be called in your overridden `Application` class.

## Manager Classes

* Provide data for the caller from the API
* Decides whether to load the data from disk or the network
    * No caching has been implemented as we have been working on the TV app
* Wraps the API and in doing so prevents API calls from other places such as views

## Sorter Classes

* Convenience class that allows multiple ways to sort a data set

## Notes

* This library is intended for use across both TV and Mobile
* There has been no caching implemented for the `Manager` classes because we are only working on the TV app at the moment
    * Caching for the `Manager` classes can be implemented at a later date (likely when mobile is implemented)