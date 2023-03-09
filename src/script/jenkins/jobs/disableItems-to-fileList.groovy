/* groovylint-disable CompileStatic, ImplicitClosureParameter, Instanceof, UnnecessaryGetter */
/*
 * Tested on CloudBees CI Managed Controller 2.375.2.3-rolling
 */
package script.jenkins.jobs

import jenkins.model.*
import com.cloudbees.hudson.plugins.folder.AbstractFolder

pathToExistingDisabledItems = '/tmp/disabledItemsv3.txt'
jenkins = Jenkins.instanceOrNull
file = new File(pathToExistingDisabledItems)
disabledItems = new StringBuilder()

jenkins.getAllItems().findAll { it instanceof ParameterizedJobMixIn.ParameterizedJob || it instanceof AbstractFolder }
    .each {
        if (it.disabled) {
            disabledItems.append("- $it.fullName\n")
        }
    }
file.withWriter('UTF-8') { writer ->
   try {
        writer.writeLine "$disabledItems"
    } finally {
        writer.close()
    }
}
println file.text
null