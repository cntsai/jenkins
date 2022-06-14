import jenkins.model.Jenkins;
import hudson.model.*

// start in the state that doesn't do any build.
def q = Jenkins.instance.queue
for (queued in Jenkins.instance.queue.items) {
  q.cancel(queued.task)
}
// stop all the currently running jobs
for (job in Jenkins.instance.items) {
  stopJobs(job)
}

def stopJobs(job) {
  if (job in com.cloudbees.hudson.plugins.folder.Folder) {
    for (child in job.items) {
      stopJobs(child)
    }
  } else if (job in org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject) {
    for (child in job.items) {
      stopJobs(child)
    }
  } else if (job in org.jenkinsci.plugins.workflow.job.WorkflowJob) {
    try {
      if (!job.isBuilding()) {
        return
      }
      for (build in job.builds) {
      	build.doKill()
      }
    } catch (Exception e) {
      println(e.toString())
    }
  }
}

def file = new File("/var/jenkins_home/jobs.txt")
def writer = file.newWriter()
disableChildren(Hudson.instance.items, writer)
writer.close()

def disableChildren(items, writer) {
  for (item in items) {
    if (item.class.canonicalName == 'hudson.model.ExternalJob') {
      continue
    }
    if (item.class.canonicalName != 'com.cloudbees.hudson.plugins.folder.Folder') {
      if (item.isDisabled()) {
        writer << "${item.name}:disabled\n"
      } else {
        writer << "${item.name}:enabled\n"
      }
      item.doDisable()
      item.save()
      println(item.name)
    } else {
      disableChildren(((com.cloudbees.hudson.plugins.folder.Folder) item).getItems(), writer)
    }
  }
}
