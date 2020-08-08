package com.example.mandar.hackerthon_app.DataResources

import java.time.Duration

/**
 * Created by mandar on 24-12-2017.
 */
class UserInformation {
    var name : String? = null
    var age : Int? = null

    var sleepDuration : Float? = null
    var weight : Float? = null
    var height : Float? = null

    var monitoringRadius : Float? = null
    var latitude : Float? = null
    var longitude : Float? = null

    var lightOn : Boolean = false
    var numberOfLights : Int = 0
    var lightR : Int = 0
    var lightG : Int = 0
    var lightB : Int = 0

    var acOn : Boolean = false
    var numberOfAC : Int = 0
    var ac : Int = 0

    var fanOn : Boolean = false
    var numberOfFans : Int = 0
    var fan : Int = 0

    var temperatureOn : Boolean = false
    var temperature : Int = 0
    var numberofTemperatureSetters : Int = 0

    var tempInput : Int = 30

    var monitoringData : String = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0"

    var lock : Boolean = false
    var lockHash : String? = null

    var token : String = ""
    var smoke : Boolean = false


    constructor(name: String,age:Int,sleepDuration: Float,weight:Float,height:Float,monitoringRadius : Float ,latitude : Double,longitude:Double,monitoringData : String){
        this.name = name
        this.age = age
        this.sleepDuration = sleepDuration
        this.weight = weight
        this.height = height

        this.monitoringRadius = monitoringRadius
        this.latitude = latitude.toFloat()
        this.longitude = longitude.toFloat()
        this.monitoringData = monitoringData
    }
    constructor(){

    }
}