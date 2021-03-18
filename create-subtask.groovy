@Grapes(
    @Grab(group='com.konghq', module='unirest-java', version='3.11.11', scope='provided')
)

import kong.unirest.JsonNode
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import kong.unirest.json.JSONObject

import static kong.unirest.Unirest.get
import static kong.unirest.Unirest.post
import static kong.unirest.Unirest.put
import static kong.unirest.Unirest.head
import static kong.unirest.Unirest.options

// Default Config
Unirest.config().defaultBaseUrl("https://hemalihshah.atlassian.net")
Unirest.config().setDefaultHeader('Authorization','Basic aGVtYWxpaHNoYWhAZ21haWwuY29tOm9TQlp3NFlzTm53clZ0UjY3a3lBRDc4Mw==')


// Console Code

println "******* Script Begin"

// Specify the key of the parent issue here
def parentKey = 'PTL-2'

// Get the parent issue type
def issueResp = get("/rest/api/2/issue/${parentKey}")
                    .asObject(Map)
assert issueResp.status == 200

// println "issueResp type : " + issueResp.class
// println "issueResp value : " + issueResp
// kong.unirest.ObjectResponse


// get the body of the parent issue type
def issue = issueResp.body as Map
// println "issue type : " + issue.class
    // null
println "** issue type : " + issue.getClass()
    // ** issue type : class com.google.gson.internal.LinkedTreeMap 
// println "issue value : " + issue

// Get the issue types for the instance
def typeResp = get('/rest/api/2/issuetype').asObject(List)
assert typeResp.status == 200
def issueTypes = typeResp.body as List<Map>
println "** issueTypes type : " + issueTypes.class
    //  ** issueTypes type : class java.util.ArrayList
// Here we set the basic subtask issue details

// Get the sub task issue type to use
def issueType = "Sub-task" // The Sub Task Issue Type to Use
def issueTypeId = issueTypes.find { it.subtask && it.name == issueType }?.id
assert issueTypeId : "No subtasks issue type found called '${issueType}'"


def summary = "Subtask summary - " + System.currentTimeMillis() // The summary to use for

// Get the project to create the subtask in
def project = (issue.fields as Map).project

// Create the subtask
def resp = post("/rest/api/2/issue")
         .header("Content-Type", "application/json")
         .body(
            fields: [
                project: project,
                issuetype: [
                    id: issueTypeId
                ],
                parent: [
                    id: issue.id
                ],
                summary: summary
            ]
        )
        .asObject(Map)

// Get and validate the newly created subtask
def subtask = resp.body
assert resp.status >= 200 && resp.status < 300 && subtask && subtask.key != null

// If the sub task created successfully return a success message along with its key
if (resp.status == 201) {
    return 'Success - Sub Task Created with the key of ' + resp.body.key.toString()
} else {
    return "${resp.status}: ${resp.body}"
}