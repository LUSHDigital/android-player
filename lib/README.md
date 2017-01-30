# Lush Player Library

This library contains common code responsible for interfacing with the Lush API.

## Setup

* The `initialise` method on `LushContent.java` must be called in your overridden `Application` class.

## Manager Classes

The manager classes are responsible for:

* Provide data for the caller from the API
* Decides whether to load the data from disk or the network
* Wraps the API and in doing so prevents API calls from other places such as views

## Notes

* There has been no disk caching implemented for the `Manager` classes because we are only working on the TV app at the moment
    * Caching for the `Manager` classes can be implemented at a later date (likely when mobile is implemented)
