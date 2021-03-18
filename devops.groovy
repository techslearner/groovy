println("*** Begining the code")

// println("*** project key value : Begining the code" + project)
// def projectKey = project.key as String

def projectKey = "SSP"

def issueTypesReq = get("/rest/api/2/issuetype").asObject(List)
assert issueTypesReq.status == 200

def taskType = issueTypesReq.body.find { it.name == "Story" }
assert taskType // Must have an issue type named Story

post("/rest/api/2/issue/bulk")
    .header("Content-Type", "application/json")
    .body(
    [
        issueUpdates: [
            [
                fields: [
                    summary    : "Review Architecture",
                    description: "First example of a bulk update",
                    project    : [
                        key : projectKey
                    ],
                    issuetype  : [
                        id: taskType.id
                    ]
                ]
            ],
            [
                fields: [
                    summary    : "Create Pipeline",
                    description: "2nd example of a bulk update",
                    project    : [
                        key: projectKey
                    ],
                    issuetype  : [
                        id: taskType.id
                    ]
                ]
            ]
        ]
    ])
    .asString()
