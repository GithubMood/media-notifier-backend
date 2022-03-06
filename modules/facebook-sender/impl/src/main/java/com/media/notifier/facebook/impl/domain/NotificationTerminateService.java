package com.media.notifier.facebook.impl.domain;

import com.media.notifier.facebook.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.facebook.impl.integration.db.repository.FacebookNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationTerminateService {
    private final FacebookNotificationRepository facebookNotificationRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void terminateNotification(Long entityId){
        var entity = facebookNotificationRepository.findByIdMandatory(entityId);
        entity.setStatus(NotificationStatus.FAILED);
        facebookNotificationRepository.save(entity);
    }
}
