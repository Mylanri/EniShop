package com.example.enishop

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.enishop.ui.screen.Article.ArticleDetail
import org.junit.Rule
import org.junit.Test

class ArticleDetailTest {
    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun testArticleDetailScreen_LoadsCorrectly() {
//        composeTestRule.setContent {
//            ArticleDetail(articleId = 1, navController = rememberNavController())
//        }
//
//        composeTestRule.onNodeWithText("Chargement en cours...").assertExists()
//    }

    @Test
    fun testArticleDetailScreen_PriceCorrectly() {
        composeTestRule.setContent {
            ArticleDetail(articleId = 1, navController = rememberNavController())
        }

        composeTestRule.onNodeWithTag("ARTICLE_PRICE_TAG")
            .assertExists()
            .assertIsDisplayed()
            .assertTextContains("109.95", substring = true)
    }
}