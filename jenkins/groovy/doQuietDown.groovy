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

disableChildren(Hudson.instance.items)

def disableChildren(items) {
  for (item in items) {
    println(item.class.canonicalName)
    if (item.class.canonicalName == 'hudson.model.ExternalJob') {
      continue
    }
    if (item.class.canonicalName != 'com.cloudbees.hudson.plugins.folder.Folder') {
      item.doDisable()
      item.save()
      println(item.name)
    } else {
      disableChildren(((com.cloudbees.hudson.plugins.folder.Folder) item).getItems())
    }
  }
}
