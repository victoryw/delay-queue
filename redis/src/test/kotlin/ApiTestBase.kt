import com.fasterxml.jackson.databind.ObjectMapper
import com.victoryw.deplayqueue.redis.interfaces.QueueOperator
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

class ApiTestBase(private val wac: WebApplicationContext,
                  queueType: String,
                  redisQueueOperator: QueueOperator,
                  protected val objectMapper: ObjectMapper) : IntegrationTestBase(redisQueueOperator, queueType) {

    protected lateinit var mockMvc: MockMvc
    @BeforeEach
    fun baseBefore() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }


}