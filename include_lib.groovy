// Generated file. Do not edit.

def scriptFile = getClass().protectionDomain.codeSource.location.toURI()
def libProjectRoot = new File(scriptFile).parentFile

def libsFile = new File(libProjectRoot, 'androidLib')
println("libsFile$libsFile")

def plugins = [File]

if (libsFile.isDirectory()) {
    plugins = libsFile.listFiles()

}

plugins.each { File file ->
    if (file.isDirectory() && file.name.indexOf(".") != 0 && file.name != "gradle") {
        def name = file.name
        def path = file.path
        def pluginDirectory = libsFile.toPath().resolve(path).toFile()
        println("pluginDirectory$pluginDirectory")
        gradle.include ":$name"
        gradle.project(":$name").projectDir = pluginDirectory
    }
}

gradle.getGradle().projectsLoaded { g ->
    g.rootProject.beforeEvaluate { p ->
        _mainModuleName = binding.variables['mainModuleName']
        if (_mainModuleName != null && !_mainModuleName.empty) {
            p.ext.mainModuleName = _mainModuleName
        }
    }
    g.rootProject.afterEvaluate { p ->
        println("p:$p")
        p.subprojects { sp ->
            println("sp:$sp")
            if (sp.name != 'app') {
                sp.evaluationDependsOn(':app')
            }
        }
    }
}
