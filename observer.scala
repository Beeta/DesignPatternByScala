
//主要核心:观察者类调用主题类的registerObserver注册,而主题类调用观察者类的update进行发布更新

import scala.collection.mutable.ArrayBuffer

abstract class Subject {
	def registerObserver(o:Observer):Unit
	def removerObserver(o:Observer):Unit
	def notifyObservers:Unit;
}

abstract class Observer{
	def update(t:Float,h:Float,p:Float):Unit
}


class WeatherData(var t:Float=0,var h:Float=0,var p:Float=0) extends Subject {
	var observers = new ArrayBuffer[Observer]
	override def registerObserver(o:Observer):Unit = {
		observers += o
	}

	override def removerObserver(o:Observer):Unit = {
		if(observers.indexOf(o)>=0) observers -= o
	}

	override def notifyObservers():Unit = {
		observers.foreach(_.update(t,h,p))
	}

	def set(t:Float,h:Float,p:Float):Unit = {
		this.t = t 
		this.h = h
		this.p = p
		notifyObservers
	}
}

class CurrentConditionsDisplay(weatherData:WeatherData,var t:Float = 0,var h:Float=0) extends Observer {
	weatherData.registerObserver(this)

	override def update(t:Float,h:Float,p:Float):Unit = {
		this.t = t 
		this.h = h
		display
	}

	def display:Unit={
		println("Current condition: "+t +"摄氏度,"+h+"%湿度")
	}
}

class CoolIndexDisplay(weatherData:WeatherData,var t:Float = 0,var h:Float=0) extends Observer{
	weatherData.registerObserver(this)

	override def update(t:Float,h:Float,p:Float):Unit = {
		this.t = t 
		this.h = h
		display
	}

	def display:Unit={
		val m = t+h
		println("Cool Index: "+m)
	}
}




val weatherData = new WeatherData
val current = new CurrentConditionsDisplay(weatherData)
val index = new CoolIndexDisplay(weatherData)
weatherData.set(1,2,3)


