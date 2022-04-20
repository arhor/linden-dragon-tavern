//package com.github.arhor.linden.dragon.tavern.service.impl
//
//import com.github.arhor.linden.dragon.tavern.TestDto
//import com.github.arhor.linden.dragon.tavern.TestEntity
//import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
//import org.assertj.core.api.Assertions.assertThat
//import org.assertj.core.api.Assertions.catchThrowable
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.BDDMockito.given
//import org.mockito.BDDMockito.then
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
//internal class WithDeleteMixinTest {
//
//    // @formatter:off
//    @Mock private lateinit var repository: CrudRepository<TestEntity, String>
//    @Mock private lateinit var testDto: TestDto
//    @Mock private lateinit var testEntity: TestEntity
//    // @formatter:on
//
//    @InjectMocks
//    private lateinit var deleterUnderTest: WithDeleteMixin<TestEntity, TestDto, String>
//
//    @Test
//    fun `should throw IllegalArgumentException`() {
//        // given
//        doReturn(null).`when`(testDto).id
//
//        // when
//        val result = catchThrowable { deleterUnderTest.delete(testDto) }
//
//        // then
//        assertThat(result)
//            .isInstanceOf(IllegalArgumentException::class.java)
//            .hasMessageContaining(WithDeleteMixin.KEY_PROPERTY)
//
//        verifyNoInteractions(repository)
//    }
//
//    @Test
//    fun `should throw EntityNotFoundException`(@com.github.arhor.linden.dragon.tavern.testutils.RandomParameter testId: String) {
//        // given
//        doReturn(testId).`when`(testDto).id
//        doReturn(Optional.empty<TestEntity>()).`when`(repository).findById(testId)
//
//        // when
//        val result = catchThrowable { deleterUnderTest.delete(testDto) }
//
//        // then
//        assertThat(result)
//            .isInstanceOf(EntityNotFoundException::class.java)
//            .hasFieldOrPropertyWithValue("propName", WithDeleteMixin.KEY_PROPERTY)
//            .hasFieldOrPropertyWithValue("propValue", testId)
//
//        verify(repository).findById(testId)
//    }
//
//    @Test
//    fun `should delete passed object without exception`(@com.github.arhor.linden.dragon.tavern.testutils.RandomParameter testId: String) {
//        // given
//        given(testDto.id)
//            .willReturn(testId)
//        given(repository.findById(testId))
//            .willReturn(Optional.of(testEntity))
//
//        // when
//        deleterUnderTest.delete(testDto)
//
//        // then
//        then(repository)
//            .should()
//            .delete(testEntity)
//    }
//}
