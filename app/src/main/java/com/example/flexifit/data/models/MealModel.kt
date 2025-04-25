package com.example.flexifit.data.models

data class MealModel(
    val from: Int,
    val to: Int,
    val count: Int,
    val _links: Links,
    val hits: List<Hit>
)

data class Links(
    val self: Link,
    val next: Link
)

data class Link(
    val href: String,
    val title: String
)

data class Hit(
    val recipe: Recipe,
    val _links: Links
)

data class Recipe(
    val uri: String,
    val label: String,
    val image: String,
    val images: RecipeImages,
    val source: String,
    val url: String,
    val shareAs: String,
    val yield: Int,
    val dietLabels: List<String>,
    val healthLabels: List<String>,
    val cautions: List<String>,
    val ingredientLines: List<String>,
    val ingredients: List<Ingredient>,
    val calories: Int,
    val glycemicIndex: Int,
    val inflammatoryIndex: Int,
    val totalCO2Emissions: Int,
    val co2EmissionsClass: String,
    val totalWeight: Int,
    val cuisineType: List<String>,
    val mealType: List<String>,
    val dishType: List<String>,
    val instructions: List<String>,
    val tags: List<String>,
    val externalId: String,
    val totalNutrients: Map<String, Nutrient>,
    val totalDaily: Map<String, Nutrient>,
    val digest: List<Digest>
)

data class RecipeImages(
    val THUMBNAIL: Image,
    val SMALL: Image,
    val REGULAR: Image,
    val LARGE: Image
)

data class Image(
    val url: String,
    val width: Int,
    val height: Int
)

data class Ingredient(
    val text: String,
    val quantity: Double,
    val measure: String,
    val food: String,
    val weight: Double,
    val foodId: String
)

data class Nutrient(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class Digest(
    val label: String,
    val tag: String,
    val schemaOrgTag: String,
    val total: Double,
    val hasRDI: Boolean,
    val daily: Double,
    val unit: String,
    val sub: String
)
