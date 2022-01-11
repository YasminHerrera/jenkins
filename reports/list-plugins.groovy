println "Lista de plugins instalados"
def plugins = jenkins.model.Jenkins.instance.getPluginManager().getPlugins()
plugins.each { println "${it.getDisplayName()} [${it.getShortName()}]: ${it.getVersion()}" }
println "\nCantidad de plugins instalados: ${plugins.size()}"
