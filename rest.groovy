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

HttpResponse<JsonNode> resp = get('/rest/api/2/issue/PTL-2')
    // .queryString("fields", "id,key,comment") 
    .asJson()

def respObj =  resp.body.object


// println '** def Id '+ respObj.key

// println '** Comments : ' + respObj.fields.comment.comments.class
// ** Comments : class kong.unirest.json.JSONArray

def fields = respObj.fields

println '** Jira Url : https://hemalihshah.atlassian.net/rest/api/2/issue/' + respObj.key
println '** Jira Key : ' + respObj.key
println '** Summary : ' + fields.summary
println '** Description : ' + fields.description
println '** Comment 1 : ' + respObj.fields.comment.comments[0].body
println '** Comment 2 : ' + respObj.fields.comment.comments[1].body
