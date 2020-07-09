data class Person(val name: String, val desc: String, val ext: String, val location: String,val company :Company){
    data class Company(val name :String ,val type:String)
}