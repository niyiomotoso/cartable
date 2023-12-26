//package com.example.cartable.integration
//
//import com.getyourguide.demo.repository.ActivityRepository
//import org.junit.jupiter.api.Test
//import org.springframework.http.MediaType
//
//@RunWith(SpringJUnit4ClassRunner::class)
//@SpringBootTest
//class ActivityApiTest {
//    @Autowired
//    var wac: WebApplicationContext? = null
//
//    @Autowired
//    var supplierRepository: SupplierRepository? = null
//
//    @Autowired
//    var activityRepository: ActivityRepository? = null
//
//    @Autowired
//    var dataPublisher: DataPublisher? = null
//    private var mockMvc: MockMvc? = null
//    @BeforeEach
//    fun setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
//        dataPublisher.parseSuppliers()
//        dataPublisher.parseActivities()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testUnknownEndpointReturns404() {
//        mockMvc.perform(MockMvcRequestBuilders.get("/unknown").contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isNotFound())
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testActivityEndpointReturns200() {
//        mockMvc.perform(MockMvcRequestBuilders.get("/activities").contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testActivityEndpointReturnsResultBasedOnLimit() {
//        val REQUEST_LIMIT = 2
//        val requestParam = "?limit=$REQUEST_LIMIT"
//        mockMvc.perform(MockMvcRequestBuilders.get("/activities$requestParam").contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath<Collection<*>>("$", Matchers.hasSize<Any>(REQUEST_LIMIT)))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testActivityEndpointReturnsResultHasAllExpectedKeys() {
//        mockMvc.perform(MockMvcRequestBuilders.get("/activities").contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[*].currency").exists()).andExpect(MockMvcResultMatchers.jsonPath("$[*].specialOffer").exists()).andExpect(MockMvcResultMatchers.jsonPath("$[*].supplierName").exists()).andExpect(MockMvcResultMatchers.jsonPath("$[*].title").exists()).andExpect(MockMvcResultMatchers.jsonPath("$[*].rating").exists()).andExpect(MockMvcResultMatchers.jsonPath("$[*].price").exists())
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testActivityEndpointReturnsResultBasedOnSearchKeyWord() {
//        val keyword = "berlin"
//        val requestParam = "?keyword=$keyword"
//        mockMvc.perform(MockMvcRequestBuilders.get("/activities$requestParam").contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath<Iterable<String>>("$[*].title", Matchers.containsInAnyOrder<String>("Skip the Line: Pergamon Museum Berlin Tickets", "Berlin WelcomeCard: Transport, Discounts & Guide Book", "Skip the Line: Berlin TV Tower Ticket")))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testActivityEndpointAlsoReturnsResultBasedOnSupplierKeyword() {
//        val keyword = "John"
//        val requestParam = "?keyword=$keyword"
//        mockMvc.perform(MockMvcRequestBuilders.get("/activities$requestParam").contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath<Iterable<String>>("$[*].supplierName", Matchers.containsInAnyOrder<String>("John Doe", "John Mark")))
//    }
//
//    @AfterEach
//    fun tearDown() {
//        supplierRepository.deleteAll()
//        activityRepository.deleteAll()
//    }
//}
