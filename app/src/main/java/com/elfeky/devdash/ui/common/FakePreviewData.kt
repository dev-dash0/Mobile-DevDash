package com.elfeky.devdash.ui.common

import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority.Companion.priorityList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.issueStatusList
import com.elfeky.devdash.ui.common.dropdown_menu.model.Type.Companion.typeList
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.comment.Comment
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.notification.Notification
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.project.UserProject
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.tenant.Tenant
import okhttp3.internal.format
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

val labelList = mutableListOf(
    "UI", "Test", "Fix", "Mobile", "DB", "Front-end", "Back-end", "Bug", "Issue"
)

val userList = (1..20).map { id ->
    UserProfile(
        id = id,
        imageUrl = if (id % 3 == 0) "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png" else if (id % 4 == 0) "https://example.com/avatars/user$id.jpeg" else if (id % 5 == 0) "https://example.com/avatars/user$id.jpg" else if (id % 7 == 0) "https://example.com/avatars/user$id.png" else null,
        email = "user$id@example.com",
        firstName = "First$id",
        lastName = "Last$id",
        userName = "user$id",
        phoneNumber = "${100 + id}-${200 + id}-${300 + id}",
        birthday = "199${id % 10}-0${id % 12 + 1}-0${id % 28 + 1}",
        personalTenantId = 1
    )
}

val companyList = List(5) { i ->
    val id = 1000 + i
    Tenant(
        description = "A dynamic and forward-thinking organization dedicated to delivering innovative solutions for company ${i + 1}.",
        id = id,
        image = if (i % 2 == 0) "https://example.com/logos/company$id.png" else null,
        joinedUsers = userList.shuffled().take(Random.nextInt(10, 20)),
        keywords = listOf("Tech", "Software", "Innovation", "Startup", "Enterprise").shuffled()
            .take(Random.nextInt(2, 4)).joinToString(", "),
        name = "Company ${'A' + i}",
        owner = userList[i % userList.size],
        ownerID = userList[i % userList.size].id,
        role = listOf("Admin", "Owner", null).random(),
        tenantCode = "COMP-${format("%03d", id)}",
        tenantUrl = "https://company${'a' + i}.example.com"
    )
}

val issueList = List(20) { i ->
    val id = 100 + i
    val creationDateTime = LocalDateTime.now().minusDays(Random.nextLong(0, 365))
    val startDateTime = creationDateTime.plusDays(Random.nextLong(0, 30))
    val deadlineDateTime = startDateTime.plusDays(Random.nextLong(7, 60))
    val assignedUsers = userList.shuffled().take(Random.nextInt(1, 4))
    val statusOptions = issueStatusList
    val priorityOptions = priorityList
    val issueTypes = typeList
    val descriptions = listOf(
        "Implement user authentication module.",
        "Fix critical bug in payment gateway.",
        "Refactor database schema for performance.",
        "Design new dashboard UI.",
        "Write unit tests for API endpoints."
    )
    val labels = labelList.shuffled().take(Random.nextInt(1, 3)).joinToString(" ")

    Issue(
        assignedUsers = assignedUsers,
        attachment = if (Random.nextBoolean()) "file$id.pdf" else null,
        attachmentPath = if (Random.nextBoolean()) "/app/attachments/file$id.pdf" else null,
        createdBy = userList[Random.nextInt(userList.size)],
        createdById = userList[Random.nextInt(userList.size)].id,
        creationDate = creationDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        deadline = deadlineDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        deliveredDate = if (Random.nextBoolean()) deadlineDateTime.minusDays(Random.nextLong(0, 7))
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) else null,
        description = descriptions.random(),
        id = id,
        isBacklog = Random.nextBoolean(),
        labels = labels,
        lastUpdate = LocalDateTime.now().minusHours(Random.nextLong(0, 24))
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        priority = priorityOptions.random().text,
        projectId = Random.nextInt(1, 5),
        sprintId = if (Random.nextBoolean()) Random.nextInt(1, 6) else null,
        startDate = startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        status = statusOptions.random().text,
        tenantId = 1000 + Random.nextInt(1, 4),
        title = "Issue $id - ${descriptions.random()}",
        type = issueTypes.random().text
    )
}

val sprintList = List(5) { i ->
    val id = i + 1
    val startDate = LocalDateTime.now().plusWeeks(i.toLong())
    val endDate = startDate.plusDays(14)
    val issuesForSprint = issueList.filter { it.sprintId == id }

    Sprint(
        createdAt = LocalDateTime.now().minusWeeks(2).plusDays(i.toLong())
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        createdBy = userList[i % userList.size],
        createdById = userList[i % userList.size].id,
        description = "Sprint $id focuses on delivering key features for Project ${i % 2 + 1}.",
        endDate = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        id = id,
        issues = issuesForSprint,
        projectId = i % 2 + 1,
        startDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        status = listOf("Active", "Completed", "Upcoming").random(),
        summary = "Summary for Sprint $id",
        tenantId = 1001,
        title = "Sprint $id - " + listOf(
            "Auth",
            "Dashboard",
            "Payments",
            "Reporting",
            "Mobile"
        ).random()
    )
}

val projectList = List(10) { i ->
    val id = i + 1
    val creationDate = LocalDate.now().minusMonths(Random.nextLong(0, 12))
    val startDate = creationDate.plusDays(Random.nextLong(0, 30))
    val endDate = startDate.plusMonths(Random.nextLong(1, 6))
    val creator = userList[Random.nextInt(userList.size)]
    val tenant = Tenant(
        name = "Preview Company Name ${Random.nextInt(1, 5)}",
        image = null,
        tenantCode = "PRE-${format("%03d", id)}",
        tenantUrl = "https://previewcompany$id.com",
        joinedUsers = userList.shuffled().take(Random.nextInt(5, 15)),
        owner = userList[0],
        keywords = "Technology,Software,Startup,FinTech".split(",").shuffled()
            .take(Random.nextInt(1, 4)).joinToString(","),
        description = "A dynamic and forward-thinking organization dedicated to delivering innovative solutions that empower businesses to achieve their full potential. We specialize in partnering with clients to understand their unique challenges and opportunities, leveraging our expertise.",
        id = 1000 + id,
        ownerID = 1,
        role = null
    )
    val statusOptions = listOf("Active", "Completed", "On Hold", "Planned")
    val priorityOptions = listOf("High", "Medium", "Low")
    val projectNames = listOf(
        "Website Redesign",
        "Mobile App Development",
        "Backend API Refactor",
        "Data Migration",
        "CRM Integration",
        "E-commerce Platform Launch",
        "AI Chatbot Implementation",
        "Cloud Infrastructure Setup",
        "Security Audit",
        "Marketing Campaign Dashboard"
    )

    val usersInProject = userList.shuffled().take(Random.nextInt(1, 5))
    val userProjects = usersInProject.map { user ->
        UserProject(
            joinedDate = LocalDate.now().minusDays(Random.nextLong(0, 180))
                .format(DateTimeFormatter.ISO_LOCAL_DATE),
            projectId = id,
            role = listOf("Developer", "Tester", "Project Manager", "Designer").random(),
            userId = user.id
        )
    }

    Project(
        creationDate = creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
        creator = creator,
        creatorId = creator.id,
        description = "This is a description for ${projectNames[i % projectNames.size]} project, focusing on ${
            listOf("user experience", "performance", "scalability", "new features").random()
        }.",
        endDate = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
        id = id,
        name = projectNames[i % projectNames.size],
        priority = priorityOptions.random(),
        projectCode = "PROJ-${format("%03d", id)}",
        role = listOf("Admin", "Member", "Viewer", null).random(),
        startDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
        status = statusOptions.random(),
        tenant = tenant,
        tenantId = tenant.id,
        userProjects = userProjects
    )
}

val commentList = List(30) { i ->
    val createdBy = userList[Random.nextInt(userList.size)]
    val creationDateTime = LocalDateTime.now().minusHours(Random.nextLong(0, 72))
    val issue = issueList[Random.nextInt(issueList.size)]

    Comment(
        content = "This is comment number ${i + 1}. ${
            listOf(
                "Great work!",
                "Needs review.",
                "Can you elaborate?",
                "Looks good to me.",
                "I have a question about this."
            ).random()
        }",
        createdBy = createdBy,
        createdById = createdBy.id,
        creationDate = creationDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        issueId = issue.id,
        projectId = issue.projectId,
        sprintId = issue.sprintId ?: Random.nextInt(
            1,
            6
        ), // Assign to a random sprint if issue is not in one
        tenantId = issue.tenantId,
        id = 1000 + i
    )
}

val notificationList = List(15) { i ->
    val issue = issueList[Random.nextInt(issueList.size)]
    val creationDateTime =
        LocalDateTime.now().minusMinutes(Random.nextLong(0, 1440))
    val messages = listOf(
        "New comment on issue: ${issue.title}",
        "Issue status updated: ${issue.title}",
        "You have been assigned to issue: ${issue.title}",
        "Issue deadline approaching: ${issue.title}",
        "Sprint started: ${sprintList.random().title}"
    )
    Notification(
        createdAt = creationDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        id = 200 + i,
        isRead = Random.nextBoolean(),
        issue = issue,
        issueId = issue.id,
        message = messages.random(),
        userId = userList.random().id
    )
}

val pinnedItems = PinnedItems(
    issues = issueList.shuffled().take(Random.nextInt(1, 4)),
    projects = projectList.shuffled().take(Random.nextInt(1, 4)),
    sprints = sprintList.shuffled().take(Random.nextInt(1, 4)),
    tenants = companyList.shuffled().take(Random.nextInt(1, 4))
)