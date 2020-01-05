package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp

/**
 * Created by Hisham Abi Farah on 04/January/2020

1. What is MVVM

MODEL


VIEW:
handles everything user touches and sees on the screen (fragments or activities)
View doesn't include business logic like network and db operations
It only displays data which it gets from the VIEWMODEL


VIEWMODEL:

provides data for the view by getting it from the repository

viewmodel doesnt know which views are using it, what viewmodel does is make data  observable
VIEWMODEL has livedata in it and the view OBSERVES that live data
when data changes, the view which is observing LIVEDATA in viewmodel will be notified about the change
the state of the data is kept in the VIEWMODEL

so even if user rotates screen the data will be fetched again because the view will receive the same instance of the viewmodel

CONTINUE VIDEO AND EXPLANATION


2. Use JSON to kotlin extension to create data classes from JSON structure
right click on package > new > kotlin data class from JSON

 */