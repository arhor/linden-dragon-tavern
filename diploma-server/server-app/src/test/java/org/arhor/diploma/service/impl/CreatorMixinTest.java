package org.arhor.diploma.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

import org.arhor.diploma.commons.Converter;
import org.arhor.diploma.commons.Identifiable;
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject;
import org.arhor.diploma.data.persistence.repository.BaseRepository;
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

@Testable
@ExtendWith({MockitoExtension.class, RandomParameter.Resolver.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class CreatorMixinTest {

    @Mock
    private Converter<DeletableDomainObject<String>, Identifiable<String>> converter;
    @Mock
    private BaseRepository<DeletableDomainObject<String>, String> repository;
    @Mock
    private Identifiable<String> testDto;
    @Mock
    private DeletableDomainObject<String> testEntity;

    @InjectMocks
    private CreatorMixin<DeletableDomainObject<String>, Identifiable<String>, String> creatorUnderTest;

    @Test
    void shouldThrowIllegalArgumentException(@RandomParameter final String testId) {
        // given
        doReturn(testId).when(testDto).getId();

        // when
        ThrowingCallable action = () -> creatorUnderTest.create(testDto);

        // then
        assertThatThrownBy(action)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(testId);

        verifyNoInteractions(repository);
    }

    @Test
    void shouldCreatePassedObjectWithoutException() {
        // given
        doReturn(null).when(testDto).getId();
        doReturn(testEntity).when(converter).mapDtoToEntity(testDto);
        doReturn(testEntity).when(repository).save(testEntity);
        doReturn(testDto).when(converter).mapEntityToDto(testEntity);

        // when
        final var result = creatorUnderTest.create(testDto);

        // then
        assertThat(result).isEqualTo(testDto);
    }
}
