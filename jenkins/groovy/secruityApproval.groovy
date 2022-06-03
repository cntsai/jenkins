import java.lang.reflect.*;
import jenkins.model.Jenkins;
import jenkins.model.*;
import org.jenkinsci.plugins.scriptsecurity.scripts.*;
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.*;

scriptApproval = ScriptApproval.get()
approveSignature()
aclApproveSignature()
scriptApproval.save()

void aclApproveSignature() {
    def signatures = [
        "method groovy.lang.Binding hasVariable java.lang.String",
        "method hudson.model.Actionable getActions java.lang.Class",
        "method java.lang.String join java.lang.CharSequence java.lang.CharSequence[]",
        "staticMethod GitHubStatus \$INIT java.lang.Object[]",
        "staticMethod Timeunit \$INIT java.lang.Object[]",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods get java.util.Map java.lang.Object java.lang.Object",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods hasProperty java.lang.Object java.lang.String"
    ]
    alreadyApproved = new HashSet<>(Arrays.asList(scriptApproval.getAclApprovedSignatures()))
    for (s in signatures) {
        if (!alreadyApproved.contains(s)) {
            scriptApproval.aclApproveSignature(s)
        }
    }
}

void approveSignature() {
    def signatures = [
        "field groovy.lang.GroovyObjectSupport metaClass",
        "field hudson.model.Slave name",
        "method groovy.json.JsonBuilder call java.util.List",
        "method groovy.json.JsonSlurper parse java.io.File",
        "method groovy.lang.GroovyObject getMetaClass",
        "method groovy.lang.GroovyObject getProperty java.lang.String",
        "method groovy.lang.GroovyObject invokeMethod java.lang.String java.lang.Object",
        "method groovy.lang.GroovyObject setProperty java.lang.String java.lang.Object",
        "method groovy.lang.GroovyShell parse java.io.File",
        "method groovy.lang.MetaMethod getName",
        "method groovy.lang.MetaObjectProtocol getProperties",
        "method groovy.lang.MetaProperty getName",
        "method groovy.util.XmlSlurper parseText java.lang.String",
        "method groovy.util.slurpersupport.GPathResult text",
        "method hudson.model.AbstractCIBase getNodes",
        "method hudson.model.Actionable getAction java.lang.Class",
        "method hudson.model.Actionable getActions java.lang.Class",
        "method hudson.model.Actionable getAllActions",
        "method hudson.model.Computer countBusy",
        "method hudson.model.Computer getOfflineCause",
        "method hudson.model.Item getFullName",
        "method hudson.model.Item getName",
        "method hudson.model.ItemGroup getItem java.lang.String",
        "method hudson.model.ItemGroup getItems",
        "method hudson.model.Job getBuilds",
        "method hudson.model.Job getProperty java.lang.String",
        "method hudson.model.Job isBuildable",
        "method hudson.model.ParameterValue getName",
        "method hudson.model.ParameterValue getValue",
        "method hudson.model.ParametersAction getParameter java.lang.String",
        "method hudson.model.ParametersAction getParameters",
        "method hudson.model.Run getId",
        "method hudson.model.Run getLog",
        "method hudson.model.Run getLog int",
        "method hudson.model.Run getNumber",
        "method hudson.model.Run isBuilding",
        "method hudson.model.Slave getComputer",
        "method hudson.slaves.OfflineCause getTime",
        "method java.io.File exists",
        "method java.io.File getName",
        "method java.lang.Class getDeclaredFields",
        "method java.lang.Class isInstance java.lang.Object",
        "method java.lang.Class newInstance",
        "method java.net.HttpURLConnection setRequestMethod java.lang.String",
        "method java.net.URL openConnection",
        "method java.net.URLConnection connect",
        "method java.net.URLConnection getContent",
        "method jenkins.model.Jenkins getComputer java.lang.String",
        "method jenkins.model.Jenkins getItemByFullName java.lang.String",
        "method jenkins.model.Jenkins getJobNames",
        "method org.jenkinsci.plugins.workflow.job.WorkflowRun doStop",
        "method org.jenkinsci.plugins.workflow.steps.FlowInterruptedException getCauses",
        "method org.jenkinsci.plugins.workflow.support.actions.EnvironmentAction getEnvironment",
        "method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild",
        "method org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildRecorder\$BadgeManager createSummary java.lang.String",
        "new Timeunit java.lang.String int java.util.LinkedHashMap",
        "new groovy.json.JsonBuilder",
        "new groovy.json.JsonSlurperClassic",
        "new groovy.lang.GroovyShell",
        "new groovy.util.XmlSlurper",
        "new java.io.File java.lang.String",
        "new java.lang.StringBuilder",
        "new java.util.HashMap java.util.Map",
        "new java.util.LinkedHashMap",
        "new org.yaml.snakeyaml.Yaml",
        "staticField org.apache.commons.csv.CSVFormat DEFAULT",
        "staticField org.apache.commons.csv.CSVFormat EXCEL",
        "staticMethod Timeunit \$INIT java.lang.Object[]",
        "staticMethod groovy.util.Eval me java.lang.String",
        "staticMethod hudson.model.Hudson getInstance",
        "staticMethod java.lang.Double isNaN",
        "staticMethod java.lang.Float isNaN",
        "staticMethod java.lang.String valueOf char[]",
        "staticMethod java.lang.System getenv java.lang.String",
        "staticMethod java.lang.Thread currentThread",
        "staticMethod java.nio.file.Paths get java.lang.String java.lang.String[]",
        "staticMethod jenkins.model.Jenkins getInstance",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods collectEntries java.util.Collection java.util.Map",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods execute java.lang.String",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods findAll java.lang.Object groovy.lang.Closure",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods first java.util.List",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods get java.util.Map java.lang.Object java.lang.Object",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods getAt java.lang.Object java.lang.String",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods getAt java.util.Collection java.lang.String",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods getProperties java.lang.Object",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods getText java.io.File java.lang.String",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods getText java.net.URL",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods inspect java.lang.Object",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods print java.lang.Object java.lang.Object",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods split java.lang.String",
        "staticMethod org.codehaus.groovy.runtime.DefaultGroovyMethods toInteger java.lang.Number",
        "staticMethod org.codehaus.groovy.transform.ImmutableASTTransformation checkPropNames java.lang.Object java.util.Map"
    ]

    alreadyApproved = new HashSet<>(Arrays.asList(scriptApproval.getApprovedSignatures()))
    for (s in signatures) {
        if (!alreadyApproved.contains(s)) {
            scriptApproval.approveSignature(s)
        }
    }
}
