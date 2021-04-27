package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.testutils.RandomParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.data.domain.Page
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@ExtendWith(MockitoExtension::class, RandomParameter.Resolver::class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ReaderMixinTest {

    @Mock private lateinit var converter: Converter<DomainObject<String>, Identifiable<String>>
    @Mock private lateinit var repository: CoroutineCrudRepository<DeletableDomainObject<String>, String>
    @Mock private lateinit var testDto: Identifiable<String>
    @Mock private lateinit var testEntity: DeletableDomainObject<String>
    @Mock private lateinit var pageResponse: Page<DeletableDomainObject<String>>

    @InjectMocks
    private lateinit var readerUnderTest: ReaderMixin<DeletableDomainObject<String>, Identifiable<String>, String>

    @Test
    fun `getList should return correct DTO list for object returned by repository`(): Unit = runBlocking {
        // given
        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)
        doReturn(flowOf(testEntity)).`when`(repository).findAll()

        // when
        val result = readerUnderTest.getList().toList()

        // then
        assertThat(result)
                .isNotNull
                .isNotEmpty
                .hasSize(1)
                .contains(testDto)

        verify(converter).mapEntityToDto(testEntity)
        verify(repository).findAll()
    }

    @Test
    fun `getOne should return correct DTO for object returned by repository`(
        // given
        @RandomParameter search: String
    ): Unit = runBlocking {
        // given
        doReturn(testDto).`when`(converter).mapEntityToDto(testEntity)
        doReturn(testEntity).`when`(repository).findById(search)

        // when
        val result = readerUnderTest.getOne(search)

        // then
        assertThat(result)
                .isNotNull
                .isSameAs(testDto)

        verify(converter).mapEntityToDto(testEntity)
        verify(repository).findById(search)
    }

//    @Test
//    fun shouldThrowAnException(@RandomParameter search: String): Unit = runBlocking {
//        // given
//        val entityType = String::class
//
//        doReturn(null).`when`(repository).findById(search);
//        doReturn(entityType).`when`(repository).getEntityType();
//
//        // when
//        ThrowingCallable action = () -> readerUnderTest.getOne(search);
//
//        // then
//        assertThatThrownBy(action)
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasFieldOrPropertyWithValue("entityType", entityType.getSimpleName())
//                .hasFieldOrPropertyWithValue("propName", ReaderMixin.KEY_PROPERTY)
//                .hasFieldOrPropertyWithValue("propValue", search);
//
//        verify(repository).findById(search);
//    }

    @Test
    fun shouldReturnSizeProvidedByRepository(): Unit = runBlocking {
        // given
        val size = 123_456_789L

        doReturn(size).`when`(repository).count()

        // when
        val result = readerUnderTest.getTotalSize()

        // then
        assertThat(result).isEqualTo(size)
    }

//    @Test
//    void shouldReturnValidDataPage() {
//        // given
//        final int page = 1;
//        final int size = 10;
//
//        final var entities = Stream.generate(() -> testEntity).limit(size).collect(toList());
//
//        doReturn(pageResponse).when(repository).findAll(any(Pageable.class));
//        doReturn(entities).when(pageResponse).toList();
//        doReturn(testDto).when(converter).mapEntityToDto(testEntity);
//
//        // when
//        final var result = readerUnderTest.getList(page, size);
//
//        // then
//        assertThat(result)
//                .isNotNull()
//                .hasSize(size);
//
//        verify(converter, times(size)).mapEntityToDto(testEntity);
//    }
}
