package org.arhor.diploma.service.impl

import kotlinx.coroutines.runBlocking
import org.arhor.diploma.TestDto
import org.arhor.diploma.TestEntity
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@ExtendWith(MockitoExtension::class, RandomParameter.Resolver::class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
internal class DeleterMixinTest {

    // @formatter:off
    @Mock private lateinit var repository: CoroutineCrudRepository<TestEntity, String>
    @Mock private lateinit var testDto: TestDto
    @Mock private lateinit var testEntity: TestEntity
    // @formatter:on

    @InjectMocks
    private lateinit var deleterUnderTest: DeleterMixin<TestEntity, TestDto, String>

    @Test
    fun `should throw IllegalArgumentException`() {
        // given
        doReturn(null).`when`(testDto).id

        // when
        val action = ThrowingCallable { runBlocking { deleterUnderTest.delete(testDto) } }

        // then
        Assertions.assertThatThrownBy(action)
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(DeleterMixin.KEY_PROPERTY)
        verifyNoInteractions(repository)
    }

    @Test
    fun `should throw EntityNotFoundException`(@RandomParameter testId: String): Unit = runBlocking {
        // given
        doReturn(testId).`when`(testDto).id
        doReturn(null).`when`(repository).findById(testId)

        // when
        val action = ThrowingCallable { runBlocking { deleterUnderTest.delete(testDto) } }

        // then
        Assertions.assertThatThrownBy(action)
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasFieldOrPropertyWithValue("propName", DeleterMixin.KEY_PROPERTY)
            .hasFieldOrPropertyWithValue("propValue", testId)

        verify(repository).findById(testId)
    }

    @Test
    fun `should delete passed object without exception`(@RandomParameter testId: String): Unit = runBlocking {
        // given
        doReturn(testId).`when`(testDto).id
        doReturn(testEntity).`when`(repository).findById(testId)

        // when
        deleterUnderTest.delete(testDto)

        // then
        verify(repository).delete(testEntity)
    }
}