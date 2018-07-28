import com.victoryw.deplayqueue.redis.fake.configuration.FakeRedisQueueEnableSolution
import com.victoryw.deplayqueue.redis.fake.gateway.interfaces.FakeDelayJobConfig
import com.victoryw.deplayqueue.redis.interfaces.QueueOperator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(FakeDelayJobConfig::class, FakeRedisQueueEnableSolution::class)
class IntegrationTestBase(
        protected val redisQueueOperator: QueueOperator,
        protected val queueType: String
) {
    @BeforeEach
    fun integrationBaseBefore() {
        redisQueueOperator.deleteQueue(queueType)
    }
}