package com.roldansworkshop.cv.firebase

enum class FirebasePath (val node: String, val path: String){
    USER_COL("users", "users"),
    PROFILE_DOC("YmR7jdeNG5JgAOOq9muG", "${USER_COL.path}/YmR7jdeNG5JgAOOq9muG"),

    PROFILE_BLLETPOINTS_COL("bulletpoints", "${PROFILE_DOC.path}/bulletpoints"),
    PROFILE_PROJECTS_COL("projects", "${PROFILE_DOC.path}/projects"),
}