package org.arhor.diploma.service.impl;

import kotlin.jvm.JvmClassMappingKt;
import org.arhor.diploma.commons.Identifiable;
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject;
import org.arhor.diploma.data.persistence.domain.core.DomainObject;
import org.arhor.diploma.data.persistence.repository.BaseRepository;
import org.arhor.diploma.exception.EntityNotFoundException;
import org.arhor.diploma.commons.Converter;
import org.arhor.diploma.testutils.RandomParameter;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Testable
@ExtendWith({MockitoExtension.class, RandomParameter.Resolver.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ReaderMixinTest {

    @Mock private Converter<DomainObject<String>, Identifiable<String>> converter;
    @Mock private BaseRepository<DeletableDomainObject<String>, String> repository;
    @Mock private Identifiable<String> testDto;
    @Mock private DeletableDomainObject<String> testEntity;
    @Mock private Page<DeletableDomainObject<String>> pageResponse;

    @InjectMocks
    private ReaderMixin<DeletableDomainObject<String>, Identifiable<String>, String> readerUnderTest;

    @Test
    void shouldReturnProperResultList() {
        // given
        doReturn(testDto).when(converter).entityToDto(testEntity);
        doReturn(List.of(testEntity)).when(repository).findAll();
        
        // when
        final var result = readerUnderTest.getList();
        
        // then
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(testDto);

        verify(converter).entityToDto(testEntity);
        verify(repository).findAll();
    }

    @Test
    void shouldReturnExactlyRequiredObject(@RandomParameter final String search) {
        // given
        doReturn(testDto).when(converter).entityToDto(testEntity);
        doReturn(Optional.of(testEntity)).when(repository).findById(search);

        // when
        final var result = readerUnderTest.getOne(search);

        // then
        assertThat(result)
                .isNotNull()
                .isSameAs(testDto);

        verify(converter).entityToDto(testEntity);
        verify(repository).findById(search);
    }

    @Test
    void shouldThrowAnException(@RandomParameter final String search) {
        // given
        final var entityType = JvmClassMappingKt.getKotlinClass(String.class);

        doReturn(Optional.empty()).when(repository).findById(search);
        doReturn(entityType).when(repository).getEntityType();

        // when
        ThrowingCallable action = () -> readerUnderTest.getOne(search);

        // then
        assertThatThrownBy(action)
                .isInstanceOf(EntityNotFoundException.class)
                .hasFieldOrPropertyWithValue("entityType", entityType.getSimpleName())
                .hasFieldOrPropertyWithValue("propName", ReaderMixin.KEY_PROPERTY)
                .hasFieldOrPropertyWithValue("propValue", search);

        verify(repository).findById(search);
    }

    @Test
    void shouldReturnSizeProvidedByRepository() {
        // given
        final long size = 123456789L;

        doReturn(size).when(repository).count();

        // when
        final long result = readerUnderTest.getTotalSize();

        // then
        assertThat(result).isEqualTo(size);
    }

    @Test
    void shouldReturnValidDataPage() {
        // given
        final int page = 1;
        final int size = 10;

        final var entities = Stream.generate(() -> testEntity).limit(size).collect(toList());

        doReturn(pageResponse).when(repository).findAll(any(Pageable.class));
        doReturn(entities).when(pageResponse).toList();
        doReturn(testDto).when(converter).entityToDto(testEntity);

        // when
        final var result = readerUnderTest.getList(page, size);

        // then
        assertThat(result)
                .isNotNull()
                .hasSize(size);

        verify(converter, times(size)).entityToDto(testEntity);
    }
}
