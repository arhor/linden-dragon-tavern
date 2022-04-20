//package com.github.arhor.linden.dragon.tavern.service.impl
//
//import com.github.arhor.linden.dragon.tavern.TestDto
//import com.github.arhor.linden.dragon.tavern.TestEntity
//import com.github.arhor.linden.dragon.tavern.common.Converter
//import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
//import org.assertj.core.api.Assertions
//import org.assertj.core.api.Assertions.assertThatThrownBy
//import org.assertj.core.api.ThrowableAssert.ThrowingCallable
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.doReturn
//import org.mockito.Mockito.verify
//import org.mockito.Mockito.verifyNoInteractions
//import org.mockito.junit.jupiter.MockitoExtension
//import org.mockito.junit.jupiter.MockitoSettings
//import org.mockito.quality.Strictness
//import org.springframework.data.repository.CrudRepository
//import java.util.Optional
//
//@ExtendWith(MockitoExtension::class, com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
//@MockitoSettings(strictness = Strictness.STRICT_STUBS)
//internal class WithUpdateMixinTest {
//
//    // @formatter:off
//    @Mock private lateinit var converter: Converter<TestEntity, TestDto>
//    @Mock private lateinit var repository: CrudRepository<TestEntity, String>
//    @Mock private lateinit var testDto: TestDto
//    @Mock private lateinit var testEntity: TestEntity
//    // @formatter:on
//
//    @InjectMocks
//    private lateinit var updaterUnderTest: WithUpdateMixin<TestEntity, TestDto, String>
//
//    @Test
//    fun `should throw IllegalArgumentException`() {
//        // given
//        doReturn(null).`when`(testDto).id
//
//        // when
//        val action = ThrowingCallable { updaterUnderTest.update(testDto) }
//
//        // then
//        assertThatThrownBy(action)
//            .isInstanceOf(IllegalArgumentException::class.java)
//            .hasMessageContaining(WithUpdateMixin.KEY_PROPERTY)
//
//        verifyNoInteractions(repository, converter)
//    }
//
//    @Test
//    fun `should throw EntityNotFoundException`(
//        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter testId: String
//    ) {
//        // given
//        doReturn(testId).`when`(testDto).id
//        doReturn(Optional.empty<TestEntity>()).`when`(repository).findById(testId)
//
//        // when
//        val action = ThrowingCallable { updaterUnderTest.update(testDto) }
//
//        // then
//        assertThatThrownBy(action)
//            .isInstanceOf(EntityNotFoundException::class.java)
//            .hasFieldOrPropertyWithValue("propName", WithUpdateMixin.KEY_PROPERTY)
//            .hasFieldOrPropertyWithValue("propValue", testId)
//
//        verify(repository).findById(testId)
//        verifyNoInteractions(converter)
//    }
//
//    @Test
//    fun `should save passed object without exception`(
//        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter testId: String
//    ) {
//        // given
//        doReturn(testId).`when`(testDto).id
//        doReturn(Optional.of(testEntity)).`when`(repository).findById(testId)
//        doReturn(testEntity).`when`(repository).save(testEntity)
//        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)
//
//        // when
//        val result = updaterUnderTest.update(testDto)
//
//        // then
//        Assertions.assertThat(result)
//            .isNotNull
//            .isEqualTo(testDto)
//    }
//}
