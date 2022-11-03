package com.agileboot.domain.system.notice;

import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.exception.ApiException;
import com.agileboot.common.exception.error.ErrorCode;
import com.agileboot.domain.common.command.BulkOperationCommand;
import com.agileboot.domain.system.notice.command.NoticeAddCommand;
import com.agileboot.domain.system.notice.command.NoticeUpdateCommand;
import com.agileboot.domain.system.notice.dto.NoticeDTO;
import com.agileboot.domain.system.notice.model.NoticeModel;
import com.agileboot.domain.system.notice.model.NoticeModelFactory;
import com.agileboot.domain.system.notice.query.NoticeQuery;
import com.agileboot.infrastructure.web.domain.login.LoginUser;
import com.agileboot.orm.entity.SysNoticeEntity;
import com.agileboot.orm.service.ISysNoticeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author valarchie
 */
@Service
public class NoticeApplicationService {

    @Autowired
    private ISysNoticeService noticeService;

    public PageDTO getNoticeList(NoticeQuery query) {
        Page<SysNoticeEntity> page = noticeService.page(query.toPage(), query.toQueryWrapper());
        List<NoticeDTO> records = page.getRecords().stream().map(NoticeDTO::new).collect(Collectors.toList());
        return new PageDTO(records, page.getTotal());
    }


    public NoticeDTO getNoticeInfo(Long id) {
        SysNoticeEntity byId = noticeService.getById(id);
        return new NoticeDTO(byId);
    }


    public void addNotice(NoticeAddCommand addCommand, LoginUser loginUser) {
        NoticeModel noticeModel = NoticeModelFactory.loadFromAddCommand(addCommand, new NoticeModel());

        noticeModel.checkFields();
        noticeModel.logCreator(loginUser);

        noticeModel.insert();
    }


    public void updateNotice(NoticeUpdateCommand updateCommand, LoginUser loginUser) {
        NoticeModel noticeModel = NoticeModelFactory.loadFromDb(updateCommand.getNoticeId(), noticeService);
        noticeModel.loadUpdateCommand(updateCommand);

        noticeModel.checkFields();
        noticeModel.logUpdater(loginUser);

        noticeModel.updateById();
    }

    public void deleteNotice(BulkOperationCommand<Long> deleteCommand) {
        noticeService.removeBatchByIds(deleteCommand.getIds());
    }




}
