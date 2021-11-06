//JENKINS SCRIPT CONSOLE
//Obtener los elementos (folders, job, etc.) que se encuentren dentro de una vista de Jenkins
def view = "TEST" //Nombre de la vista

println "Lista de elementos que se encuentran en la vista: " + view

hudson.model.Hudson.instance.getView(view).items.each() { 
  println "Elemento: " + it.fullDisplayName + ", y es de tipo: " + it.class
}
