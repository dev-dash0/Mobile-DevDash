package com.elfeky.devdash.ui.common.dialogs

import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.common.dialogs.project.model.CompanyUiModel

val labelList = mutableListOf(
    "UI", "Test", "Fix", "Mobile", "DB", "Front-end", "Back-end", "Bug", "Issue"
)

val assigneeList = listOf(
    UserUiModel("Mohamed", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    UserUiModel("Amira", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    UserUiModel("Hossam", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    UserUiModel("Youssef", ""),
    UserUiModel("Ahmed", "")
)

val companyList = listOf(
    CompanyUiModel("Google", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("Microsoft", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("Meta", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("OpenAI", ""),
    CompanyUiModel("Intel", "")
)

