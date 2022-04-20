//package com.github.arhor.linden.dragon.tavern.service.impl
//
//import com.github.arhor.linden.dragon.tavern.TestDto
//import com.github.arhor.linden.dragon.tavern.TestEntity
//import com.github.arhor.linden.dragon.tavern.common.Converter
//import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
//import org.assertj.core.api.Assertions.assertThat
//import org.assertj.core.api.Assertions.assertThatThrownBy
//import org.assertj.core.api.ThrowableAssert.ThrowingCallable
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.doReturn
//import org.mockito.Mockito.verify
//import org.mockito.junit.jupiter.MockitoExtension
//import org.mockito.junit.jupiter.MockitoSettings
//import org.mockito.quality.Strictness
//import org.springframework.data.repository.CrudRepository
//import java.util.Optional
//
//@ExtendWith(MockitoExtension::class, com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
//@MockitoSettings(strictness = Strictness.STRICT_STUBS)
//internal class WithReadMixinTest {
//
//    // @formatter:off
//    @Mock private lateinit var converter: Converter<TestEntity, TestDto>
//    @Mock private lateinit var repository: CrudRepository<TestEntity, String>
//    @Mock private lateinit var testDto: TestDto
//    @Mock private lateinit var testEntity: TestEntity
//    // @formatter:on
//
//    @InjectMocks
//    private lateinit var readerUnderTest: WithReadMixin<TestEntity, TestDto, String>
//
//    @Test
//    fun `should return correct DTO list for object returned by repository`() {
//        // given
//        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)
//        doReturn(listOf(testEntity)).`when`(repository).findAll()
//
//        // when
//        val result = readerUnderTest.getList().toList()
//
//        // then
//        assertThat(result)
//            .isNotNull
//            .isNotEmpty
//            .hasSize(1)
//            .contains(testDto)
//
//        verify(converter).mapEntityToDto(testEntity)
//        verify(repository).findAll()
//    }
//
//    @Test
//    fun `should return correct DTO for object returned by repository`(
//        @com.github.arhor.linden.dragon.tavern.testutils.RandomParameter search: String
//    ) {
//        // given
//        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)
//        doReturn(Optional.of(testEntity)).`when`(repository).findById(search)
//
//        // when
//        val result = readerUnderTest.getOne(search)
//
//        // then
//        assertThat(result)
//            .isNotNull
//            .isSameAs(testDto)
//
//        verify(converter).mapEntityToDto(testEntity)
//        verify(repository).findById(search)
//    }
//
//    @Test
//    fun `should throw EntityNotFoundException`(@com.github.arhor.linden.dragon.tavern.testutils.RandomParameter search: String) {
//        // given
//        doReturn(Optional.empty<TestEntity>()).`when`(repository).findById(search)
//
//        // when
//        val action = ThrowingCallable { readerUnderTest.getOne(search) }
//
//        // then
//        assertThatThrownBy(action)
//            .isInstanceOf(EntityNotFoundException::class.java)
//            .hasFieldOrPropertyWithValue("propName", WithReadMixin.KEY_PROPERTY)
//            .hasFieldOrPropertyWithValue("propValue", search)
//
//        verify(repository).findById(search)
//    }
//
//    @Test
//    fun `should return size provided by repository`(@com.github.arhor.linden.dragon.tavern.testutils.RandomParameter size: Long) {
//        // given
//        doReturn(size).`when`(repository).count()
//
//        // when
//        val result = readerUnderTest.getTotalSize()
//
//        // then
//        assertThat(result).isEqualTo(size)
//    }
//}
