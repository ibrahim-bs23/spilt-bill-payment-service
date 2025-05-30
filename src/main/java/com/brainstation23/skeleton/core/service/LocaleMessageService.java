package com.brainstation23.skeleton.core.service;

import com.brainstation23.skeleton.core.domain.exceptions.RecordNotFoundException;
import com.brainstation23.skeleton.core.domain.model.LocaleMessageDto;
import com.brainstation23.skeleton.data.entity.LocaleMessage;
import com.brainstation23.skeleton.core.domain.enums.ResponseMessage;
import com.brainstation23.skeleton.data.repository.LocaleMessageRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocaleMessageService {

    private final HttpServletRequest request;
    private final MessageSource messageSource;
    private final LocaleMessageRepository localeMessageRepository;

    public Locale getLocale() {
        return request.getLocale();
    }

    public String getLocalMessage(ResponseMessage key, Object... objects) {
        return messageSource.getMessage(key.getResponseMessage(), objects, getLocale());
    }

    public String getLocalMessage(ResponseMessage key) {
        return messageSource.getMessage(key.getResponseMessage(), null, getLocale());
    }

    public String getLocalMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, getLocale());
    }

    public String getLocalMessage(String key) {
        return messageSource.getMessage(key, null, getLocale());
    }


    public LocaleMessageDto getAllMessages(String languageCode) {

        Optional<LocaleMessage> messageOpt = localeMessageRepository.findByLocale(languageCode);
        if (messageOpt.isEmpty())
            throw new RecordNotFoundException(ResponseMessage.LOCALE_RECORD_NOT_FOUND);

        return new LocaleMessageDto(messageOpt.get().getLocale(), messageOpt.get().getContent());

    }
}
