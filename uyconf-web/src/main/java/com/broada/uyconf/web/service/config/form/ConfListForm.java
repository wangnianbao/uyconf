package com.broada.uyconf.web.service.config.form;

import javax.validation.constraints.NotNull;

import com.broada.dsp.common.form.RequestListBase;

import lombok.Data;

/**
 * @author wnb
 *
 */
@Data
public class ConfListForm extends RequestListBase {

    /**
     *
     */
    private static final long serialVersionUID = -2498128894396346299L;

    @NotNull
    private Long appId;

    @NotNull
    private String version;

    @NotNull
    private Long envId;

}
