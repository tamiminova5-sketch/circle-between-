package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.appendInlineContent
import com.example.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, 
    unreadNotificationCount: Int = 0,
    onNavigateToSearch: () -> Unit, 
    onNavigateToProduct: (String) -> Unit, 
    onNavigateToCategory: () -> Unit = {},
    onNavigateToCircleDeals: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    
    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        HomeHeader(
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToNotification = onNavigateToNotification,
            unreadNotificationCount = unreadNotificationCount
        )
        
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            HeroBanner()
            CircleDealsSection(onNavigateToProduct = onNavigateToProduct, onNavigateToCircleDeals = onNavigateToCircleDeals)
            CategorySection(onNavigateToCategory = onNavigateToCategory)
            JustForYouSection(onNavigateToProduct = onNavigateToProduct)
            BenefitsSection()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun HomeHeader(
    onNavigateToSearch: () -> Unit,
    onNavigateToNotification: () -> Unit = {},
    unreadNotificationCount: Int = 0
) {
    var searchQuery by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo
        Row(
            modifier = Modifier.padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingBag,
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(
                    text = "CIRCLE",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
                Text(
                    text = "BAZAR",
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            }
        }
        
        Box(modifier = Modifier.weight(1f).height(48.dp).clickable { onNavigateToSearch() }) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxSize(),
                placeholder = { Text("Search products...", fontSize = 13.sp, color = Color.Gray, maxLines = 1) },
                shape = RoundedCornerShape(24.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray, modifier = Modifier.size(20.dp)) },
                trailingIcon = {
                    Row(modifier = Modifier.padding(end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Image Search", tint = Color.Gray, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.Mic, contentDescription = "Voice Search", tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.LightGray.copy(alpha = 0.5f),
                    disabledContainerColor = Color.White,
                    disabledTextColor = Color.Black,
                    disabledPlaceholderColor = Color.Gray,
                    disabledLeadingIconColor = Color.Gray,
                    disabledTrailingIconColor = Color.Gray
                )
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        IconButton(onClick = onNavigateToNotification, modifier = Modifier.size(32.dp)) {
            BadgedBox(
                badge = {
                    if (unreadNotificationCount > 0) {
                        Badge(
                            containerColor = Color(0xFF4CAF50),
                            modifier = Modifier.offset(x = 4.dp, y = 4.dp)
                        ) {
                            Text(unreadNotificationCount.toString(), color = Color.White, fontSize = 10.sp)
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Outlined.Notifications, 
                    contentDescription = "Notifications", 
                    tint = if (unreadNotificationCount > 0) Color(0xFF4CAF50) else Color.Black
                )
            }
        }
    }
}

@Composable
fun HeroBanner() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % 3
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(modifier = Modifier.fillMaxWidth().padding(top = 0.dp, bottom = 4.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.2f)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val imageRes = when(page) {
                    0 -> R.drawable.img_hero_banner_new
                    1 -> R.drawable.img_hero_banner
                    else -> R.drawable.img_hero_banner_new
                }
                
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Promo Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    val subtitle = when(page) {
                        0 -> "Special Offer"
                        1 -> "New Arrival"
                        else -> "Limited Time"
                    }
                    
                    val title = when(page) {
                        0 -> "MEGA SALE"
                        1 -> "TRENDING NOW"
                        else -> "FLASH DEAL"
                    }

                    Text(
                        text = subtitle,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 28.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "UP TO ",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp
                        )
                        Text(
                            text = "70% OFF",
                            color = Color(0xFFFFEB3B),
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { /* Shop Now */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Shop Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
        
        // Indicator Dots
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 16.dp else 8.dp, 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (pagerState.currentPage == index) Color.White else Color.White.copy(alpha = 0.5f))
                )
            }
        }
    }
}

@Composable
fun CircleDealsSection(onNavigateToProduct: (String) -> Unit, onNavigateToCircleDeals: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val cardWidth = configuration.screenWidthDp.dp * 0.23f
    val cardHeight = configuration.screenHeightDp.dp * 0.20f

    Column(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.FlashOn,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Circle Deals",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onNavigateToCircleDeals() }
            ) {
                Text("Shop More", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TimeBox("12", "HRS")
            TimeBox("48", "MINS")
            TimeBox("36", "SECS")
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val deals = listOf(
                Deal("Haylou Solar Lite Smart Watch", "৳2,450", "৳4,450", R.drawable.img_product_watch, "-45%", 0.1f, "Only 10 Left"),
                Deal("Pro Wireless Earbuds", "৳1,250", "৳2,000", R.drawable.img_product_headphones, "-38%", 0.05f, "Only 5 Left"),
                Deal("Women's Premium Hand Bag", "৳1,290", "৳3,150", R.drawable.img_product_shoes, "-40%", 0.08f, "Only 8 Left"),
                Deal("Luxury Perfume For Women", "৳1,450", "৳2,900", R.drawable.img_product_watch, "-50%", 0.15f, "Only 7 Left")
            )
            items(deals) { deal ->
                CircleDealProductCard(
                    title = deal.title,
                    price = deal.price,
                    oldPrice = deal.oldPrice,
                    imageRes = deal.imageRes,
                    discount = deal.discount,
                    progress = deal.progress,
                    leftText = deal.leftText,
                    onNavigateToProduct = { onNavigateToProduct("deal_1") },
                    modifier = Modifier.width(cardWidth).height(cardHeight)
                )
            }
        }
    }
}

data class Deal(
    val title: String, val price: String, val oldPrice: String,
    val imageRes: Int, val discount: String, val progress: Float, val leftText: String
)

@Composable
fun TimeBox(number: String, label: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFFFE066))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = number, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp, lineHeight = 15.sp)
        Text(text = label, color = Color.Black, fontSize = 8.sp, fontWeight = FontWeight.Bold, lineHeight = 10.sp)
    }
}

@Composable
fun CategorySection(onNavigateToCategory: () -> Unit = {}) {
    val categories = listOf<Triple<String, ImageVector, Color>>(
        Triple("Electronics", Icons.Outlined.Headphones, Color(0xFF1976D2)),
        Triple("Fashion", Icons.Outlined.Checkroom, Color(0xFF43A047)),
        Triple("Home & Living", Icons.Outlined.Weekend, Color(0xFFF57C00)),
        Triple("Beauty", Icons.Outlined.Face, Color(0xFFD81B60)),
        Triple("Groceries", Icons.Outlined.ShoppingBasket, Color(0xFF7CB342)),
        Triple("Mobiles", Icons.Outlined.PhoneIphone, Color(0xFF1E88E5)),
        Triple("Appliances", Icons.Outlined.LocalLaundryService, Color(0xFF546E7A)),
        Triple("Baby & Kids", Icons.Outlined.ChildCare, Color(0xFF8D6E63)),
        Triple("Sports", Icons.Outlined.SportsSoccer, Color(0xFF212121)),
        Triple("Automotive", Icons.Outlined.DirectionsCar, Color(0xFF455A64)),
        Triple("Books", Icons.Outlined.MenuBook, Color(0xFF5E35B1)),
        Triple("More", Icons.Outlined.GridView, Color(0xFF388E3C))
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp, horizontal = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categories.chunked(6).forEach { rowCategories ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowCategories.forEach { (name, icon, color) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable(enabled = name == "More") { onNavigateToCategory() }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .border(1.dp, Color.LightGray.copy(alpha = 0.3f), CircleShape)
                                    .shadow(elevation = 1.dp, shape = CircleShape, spotColor = Color.Red.copy(alpha = 0.05f))
                                    .clip(CircleShape)
                                    .background(if (name == "More") color else Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = name.replace("\n", " "),
                                    tint = if (name == "More") Color.White else color,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = name,
                                fontSize = 9.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                lineHeight = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

data class JFYProduct(
    val id: String,
    val title: String,
    val imageRes: Int,
    val price: String,
    val oldPrice: String?,
    val discount: String?,
    val rating: Float,
    val soldCount: Int
)

@Composable
fun JustForYouSection(onNavigateToProduct: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(
            text = "Just For You",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
        )
        
        // Mock Grid since LazyVerticalGrid inside Scrollable Column is tricky without fixed height
        val products = listOf(
            JFYProduct("j1", "Premium Over-Ear Headphones Pro Max Series", R.drawable.img_product_headphones, "৳2,450", "৳4,450", "-45%", 4.9f, 1200),
            JFYProduct("j2", "Luxury Men's Watch", R.drawable.img_product_watch, "৳1,250", null, null, 4.8f, 850),
            JFYProduct("j3", "Minimal White Sneakers High Quality", R.drawable.img_product_shoes, "৳1,290", "৳3,150", "-40%", 4.7f, 340),
            JFYProduct("j4", "Premium Headphones Pro", R.drawable.img_product_headphones, "৳1,450", "৳2,900", "-50%", 4.9f, 560)
        )
        
        products.chunked(2).forEach { rowProducts ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowProducts.forEach { product ->
                    ProductCard(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        oldPrice = product.oldPrice,
                        imageRes = product.imageRes,
                        discount = product.discount,
                        rating = product.rating,
                        soldCount = product.soldCount,
                        onNavigateToProduct = onNavigateToProduct,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun BenefitsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BenefitItem(Icons.Outlined.LocalShipping, "Free\nDelivery")
        BenefitItem(Icons.Outlined.CheckCircle, "Best\nQuality")
        BenefitItem(Icons.Outlined.Security, "Secure\nPayment")
        BenefitItem(Icons.Outlined.Payment, "Easy\nReturns")
    }
}

@Composable
fun BenefitItem(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontSize = 11.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 14.sp)
    }
}

@Composable
fun ProductCard(
    id: String,
    title: String,
    price: String,
    oldPrice: String?,
    imageRes: Int,
    discount: String?,
    rating: Float,
    soldCount: Int,
    onNavigateToProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    isCircleDeal: Boolean = false,
    progress: Float? = null,
    leftText: String? = null,
    ratingCount: Int = 120
) {
    Card(
        modifier = modifier
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(12.dp), spotColor = Color.Red.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onNavigateToProduct(id) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Favorite Icon
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.9f))
                        .clickable { /* Toggle Wishlist */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Wishlist", tint = Color.Gray, modifier = Modifier.size(18.dp))
                }
                
                // Discount Badge
                if (discount != null) {
                    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
                    val dWidth = (configuration.screenWidthDp * 0.06).dp
                    val dHeight = (configuration.screenHeightDp * 0.02).dp
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp, start = 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE53935))
                            .size(dWidth, dHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = discount,
                            color = Color.White, 
                            fontSize = 8.sp, 
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            softWrap = false,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                }
            }
            
            Column(modifier = Modifier.padding(12.dp)) {
                val annotatedTitle = androidx.compose.ui.text.buildAnnotatedString {
                    if (isCircleDeal) {
                        appendInlineContent("circle_deal")
                        append(" ")
                    }
                    append(title)
                }
                
                val inlineContent = mapOf(
                    "circle_deal" to androidx.compose.foundation.text.InlineTextContent(
                        androidx.compose.ui.text.Placeholder(
                            width = (LocalConfiguration.current.screenWidthDp * 0.11f).sp,
                            height = (LocalConfiguration.current.screenHeightDp * 0.015f).sp,
                            placeholderVerticalAlign = androidx.compose.ui.text.PlaceholderVerticalAlign.TextCenter
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF388E3C)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Circle Deals",
                                color = Color.White,
                                fontSize = 6.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                style = androidx.compose.ui.text.TextStyle(
                                    platformStyle = androidx.compose.ui.text.PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        }
                    }
                )

                Text(
                    text = annotatedTitle,
                    inlineContent = inlineContent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "$rating ($ratingCount)", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(2.dp))
                
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (oldPrice != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = oldPrice,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                }
                Text(text = "$soldCount sold", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                
                if (progress != null && leftText != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                        color = Color(0xFFE53935),
                        trackColor = Color(0xFFFFEBEE)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = leftText,
                        fontSize = 9.sp,
                        color = Color(0xFFE53935),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun CircleDealProductCard(
    title: String,
    price: String,
    oldPrice: String,
    imageRes: Int,
    discount: String,
    progress: Float,
    leftText: String,
    onNavigateToProduct: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp), spotColor = Color.Red.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onNavigateToProduct() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize().padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 0.dp),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.BottomCenter
                )
                
                // Discount Badge - Custom Shape Red Background
                val configuration = androidx.compose.ui.platform.LocalConfiguration.current
                val dWidth = (configuration.screenWidthDp * 0.06).dp
                val dHeight = (configuration.screenHeightDp * 0.02).dp
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 8.dp, start = 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE53935))
                        .size(dWidth, dHeight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = discount, 
                        color = Color.White, 
                        fontSize = 8.sp, 
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                }
                
                // Favorite Icon
                Icon(
                    Icons.Default.FavoriteBorder, 
                    contentDescription = "Wishlist", 
                    tint = Color.Gray, 
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(16.dp)
                )
            }
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 4.dp, top = 0.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                val annotatedTitle = androidx.compose.ui.text.buildAnnotatedString {
                    appendInlineContent("circle_deal")
                    append(" ")
                    append(title)
                }
                
                val inlineContent = mapOf(
                    "circle_deal" to androidx.compose.foundation.text.InlineTextContent(
                        androidx.compose.ui.text.Placeholder(
                            width = (LocalConfiguration.current.screenWidthDp * 0.11f).sp,
                            height = (LocalConfiguration.current.screenHeightDp * 0.015f).sp,
                            placeholderVerticalAlign = androidx.compose.ui.text.PlaceholderVerticalAlign.TextCenter
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFF388E3C)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Circle Deals",
                                color = Color.White,
                                fontSize = 6.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                style = androidx.compose.ui.text.TextStyle(
                                    platformStyle = androidx.compose.ui.text.PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        }
                    }
                )

                Text(
                    text = annotatedTitle,
                    inlineContent = inlineContent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )
                
                Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(top = 2.dp)) {
                    Text(
                        text = price,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = oldPrice,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                }
                
                // Progress Bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp).height(5.dp).clip(RoundedCornerShape(2.5.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.LightGray.copy(alpha = 0.5f),
                )
                
                Text(
                    text = leftText,
                    color = Color(0xFFE53935), // Red
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 2.dp)
                )
            }
        }
    }
}
