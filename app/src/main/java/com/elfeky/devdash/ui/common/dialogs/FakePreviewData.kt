package com.elfeky.devdash.ui.common.dialogs

import com.elfeky.devdash.ui.common.dialogs.project.model.CompanyUiModel
import com.elfeky.domain.model.account.UserProfile

val labelList = mutableListOf(
    "UI", "Test", "Fix", "Mobile", "DB", "Front-end", "Back-end", "Bug", "Issue"
)

val userList =
    listOf(
        UserProfile(
            id = 1,
            imageUrl = "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
            email = "john.doe@example.com",
            firstName = "John",
            lastName = "Doe",
            userName = "johndoe",
            phoneNumber = "123-456-7890",
            birthday = "1990-05-15"
        ),
        UserProfile(
            id = 2,
            imageUrl = null, // Example with no image URL
            email = "jane.smith@example.com",
            firstName = "Jane",
            lastName = "Smith",
            userName = "janesmith",
            phoneNumber = "987-654-3210",
            birthday = "1992-11-20"
        ),
        UserProfile(
            id = 3,
            imageUrl = "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
            email = "peter.jones@example.com",
            firstName = "Peter",
            lastName = "Jones",
            userName = "peterj",
            phoneNumber = "555-123-4567",
            birthday = "1985-07-01"
        ),
        UserProfile(
            id = 4,
            imageUrl = "https://example.com/avatars/user4.jpeg",
            email = "mary.brown@example.com",
            firstName = "Mary",
            lastName = "Brown",
            userName = "maryb",
            phoneNumber = "111-222-3333",
            birthday = "1998-03-10"
        ),
        UserProfile(
            id = 5,
            imageUrl = "https://example.com/avatars/user5.jpg",
            email = "david.lee@example.com",
            firstName = "David",
            lastName = "Lee",
            userName = "davidl",
            phoneNumber = "444-555-6666",
            birthday = "1995-09-25"
        ),
        UserProfile(
            id = 6,
            imageUrl = null,
            email = "susan.wang@example.com",
            firstName = "Susan",
            lastName = "Wang",
            userName = "susanw",
            phoneNumber = "777-888-9999",
            birthday = "1988-01-05"
        ),
        UserProfile(
            id = 7,
            imageUrl = "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
            email = "michael.kim@example.com",
            firstName = "Michael",
            lastName = "Kim",
            userName = "michaelk",
            phoneNumber = "222-333-4444",
            birthday = "1993-12-18"
        ),
        UserProfile(
            id = 8,
            imageUrl = "https://example.com/avatars/user8.jpg",
            email = "emily.davis@example.com",
            firstName = "Emily",
            lastName = "Davis",
            userName = "emilyd",
            phoneNumber = "333-444-5555",
            birthday = "2000-06-30"
        ),
        UserProfile(
            id = 9,
            imageUrl = null,
            email = "chris.wilson@example.com",
            firstName = "Chris",
            lastName = "Wilson",
            userName = "chrisw",
            phoneNumber = "666-777-8888",
            birthday = "1982-02-14"
        ),
        UserProfile(
            id = 10,
            imageUrl = "https://example.com/avatars/user10.png",
            email = "olivia.taylor@example.com",
            firstName = "Olivia",
            lastName = "Taylor",
            userName = "oliviat",
            phoneNumber = "999-000-1111",
            birthday = "1997-08-03"
        ),
        UserProfile(
            id = 11,
            imageUrl = "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
            email = "james.anderson@example.com",
            firstName = "James",
            lastName = "Anderson",
            userName = "jamesa",
            phoneNumber = "111-999-8888",
            birthday = "1991-04-22"
        ),
        UserProfile(
            id = 12,
            imageUrl = null,
            email = "sophia.thomas@example.com",
            firstName = "Sophia",
            lastName = "Thomas",
            userName = "sophiat",
            phoneNumber = "222-888-7777",
            birthday = "1989-10-11"
        ),
        UserProfile(
            id = 13,
            imageUrl = "https://example.com/avatars/user13.jpg",
            email = "daniel.jackson@example.com",
            firstName = "Daniel",
            lastName = "Jackson",
            userName = "danielj",
            phoneNumber = "333-777-6666",
            birthday = "1996-01-29"
        ),
        UserProfile(
            id = 14,
            imageUrl = "https://example.com/avatars/user14.png",
            email = "ava.white@example.com",
            firstName = "Ava",
            lastName = "White",
            userName = "avaw",
            phoneNumber = "444-666-5555",
            birthday = "1994-07-07"
        ),
        UserProfile(
            id = 15,
            imageUrl = null,
            email = "ethan.harris@example.com",
            firstName = "Ethan",
            lastName = "Harris",
            userName = "ethanh",
            phoneNumber = "555-555-4444",
            birthday = "2001-03-19"
        ),
        UserProfile(
            id = 16,
            imageUrl = "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
            email = "mia.martin@example.com",
            firstName = "Mia",
            lastName = "Martin",
            userName = "miam",
            phoneNumber = "666-444-3333",
            birthday = "1987-12-01"
        ),
        UserProfile(
            id = 17,
            imageUrl = "https://example.com/avatars/user17.jpg",
            email = "alexander.garcia@example.com",
            firstName = "Alexander",
            lastName = "Garcia",
            userName = "alexg",
            phoneNumber = "777-333-2222",
            birthday = "1999-05-08"
        ),
        UserProfile(
            id = 18,
            imageUrl = null,
            email = "chloe.rodriguez@example.com",
            firstName = "Chloe",
            lastName = "Rodriguez",
            userName = "chloer",
            phoneNumber = "888-222-1111",
            birthday = "1990-11-25"
        ),
        UserProfile(
            id = 19,
            imageUrl = "https://example.com/avatars/user19.png",
            email = "william.martinez@example.com",
            firstName = "William",
            lastName = "Martinez",
            userName = "williamm",
            phoneNumber = "999-111-0000",
            birthday = "1984-06-14"
        ),
        UserProfile(
            id = 20,
            imageUrl = "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png",
            email = "grace.hernandez@example.com",
            firstName = "Grace",
            lastName = "Hernandez",
            userName = "graceh",
            phoneNumber = "000-999-8888",
            birthday = "1993-02-07"
        )
    )

val companyList = listOf(
    CompanyUiModel("Google", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("Microsoft", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("Meta", "https://freesvg.org/img/publicdomainq-0006224bvmrqd.png"),
    CompanyUiModel("OpenAI", ""),
    CompanyUiModel("Intel", "")
)

