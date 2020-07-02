-- drop table t_l_pay_logs;
-- drop table t_d_return_error;


CREATE TABLE `t_l_pay_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `req_content` text DEFAULT NULL COMMENT '请求内容',
  `res_result` text DEFAULT NULL COMMENT '返回结果',
  `res_code` VARCHAR(32) DEFAULT NULL COMMENT '响应code',
  `req_type` varchar(32) DEFAULT NULL COMMENT '请求类型',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '支付日志记录';

CREATE TABLE `t_s_system_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `type_name` VARCHAR(255) DEFAULT NULL COMMENT '类型名称',
  `set_value` VARCHAR(255) DEFAULT NULL COMMENT '设置值',
	`is_use` char(1) DEFAULT 0 COMMENT '是否启用：0-关闭 1-开启',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录修改时间',
  `create_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  `update_user` VARCHAR(32) DEFAULT NULL COMMENT '',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '系统设置';



CREATE TABLE `t_d_return_error` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `error_code` VARCHAR(255) DEFAULT NULL COMMENT '错误码',
  `error_desc` VARCHAR(500) DEFAULT NULL COMMENT '错误描述',
  `error_owner` int(11) DEFAULT NULL COMMENT '所属模块',
  `error_type` int(11) DEFAULT 1 COMMENT '错误类型:1-为自己错误',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_error_code` (`error_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '华夏银行，错误码对应表';

CREATE TABLE `t_s_user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` VARCHAR(32) DEFAULT NULL COMMENT '用户code',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '汇款人',
  `amount` decimal(11,2) DEFAULT NULL COMMENT '余额',
  `frozen` decimal(11,2) DEFAULT NULL COMMENT '冻结金额',
  `is_use` char(1) DEFAULT 1 COMMENT '账户状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '基础账户信息表';


CREATE TABLE `t_d_bank_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_no` VARCHAR(32) DEFAULT NULL COMMENT '机构行号',
  `org_name` VARCHAR(32) DEFAULT NULL COMMENT '机构名称',
  `net_bank_no` VARCHAR(32) DEFAULT NULL COMMENT '网银行号',
  `clean_bank_no` VARCHAR(32) DEFAULT NULL COMMENT '清算行号',
  `bank_status` VARCHAR(32) DEFAULT NULL COMMENT '状态',
  `bank_type` VARCHAR(32) DEFAULT 1 COMMENT '是否对接跨行清算系统',
  `org_type` VARCHAR(32) DEFAULT 1 COMMENT '错误类型:1-为自己错误',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_org_name` (`org_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '银行信息表';

CREATE TABLE `t_d_area_info_hfb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_name` VARCHAR(32) DEFAULT NULL COMMENT '市',
  `child_name` VARCHAR(32) DEFAULT NULL COMMENT '区',
  `is_use` char(1) DEFAULT 1 COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '地区信息表';


CREATE TABLE `t_d_bank_info_hfb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_no` VARCHAR(32) DEFAULT NULL COMMENT '机构编号',
  `org_name` VARCHAR(32) DEFAULT NULL COMMENT '机构名称',
  `is_use` char(1) DEFAULT 1 COMMENT '机构名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_org_name` (`org_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '汇付宝银行信息表';


ALTER TABLE t_s_personal_income_tax add pay_type int(5) COLLATE utf8_bin DEFAULT 0 COMMENT '0-华夏银行 1-汇付宝';
ALTER TABLE t_s_commission_detail_zn add province VARCHAR(50) COLLATE utf8_bin DEFAULT 0 COMMENT '省份';
ALTER TABLE t_s_commission_detail_zn add city VARCHAR(50) COLLATE utf8_bin DEFAULT 0 COMMENT '城市';
ALTER TABLE t_s_commission_detail_zn add rece_bank_info VARCHAR(500) COLLATE utf8_bin DEFAULT 0 COMMENT '收款支行信息';

ALTER TABLE t_s_cash_withdrawal_zn add province VARCHAR(50) COLLATE utf8_bin DEFAULT 0 COMMENT '省份';
ALTER TABLE t_s_cash_withdrawal_zn add city VARCHAR(50) COLLATE utf8_bin DEFAULT 0 COMMENT '城市';
ALTER TABLE t_s_cash_withdrawal_zn add rece_bank_info VARCHAR(500) COLLATE utf8_bin DEFAULT 0 COMMENT '收款支行信息';
ALTER TABLE t_s_cash_withdrawal_zn add payment_channel char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '支付渠道 1银行 2三方';

-- drop TABLE t_s_pay_record_detail,t_s_pay_record;

CREATE TABLE `t_s_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ccode` VARCHAR(32) DEFAULT NULL COMMENT 'ccode',
  `tran_code` VARCHAR(32) DEFAULT NULL COMMENT '交易代码',
  `oper_name` VARCHAR(32) DEFAULT NULL COMMENT '企业经办人20位：非必填，不发银行，此处记录user_code：32位',
  `entry_date` VARCHAR(32) DEFAULT NULL COMMENT '录入日期：YYYYMMDD日期只能为当天',
  `batch_no` VARCHAR(20) DEFAULT NULL COMMENT '企业端批次号',
  `pay_acct_no` VARCHAR(22) DEFAULT NULL COMMENT '付款账号',
  `book_no` VARCHAR(22) DEFAULT NULL COMMENT '账簿号',
  `summary` VARCHAR(255) DEFAULT NULL COMMENT '摘要',
  `note1` VARCHAR(255) DEFAULT NULL COMMENT '备用域1',
  `note2` VARCHAR(255) DEFAULT NULL COMMENT '备用域2',
  `note3` VARCHAR(255) DEFAULT NULL COMMENT '备用域3',
  `amount` decimal(11,2) DEFAULT NULL COMMENT '总金额',
  `rec_num` int(11) DEFAULT null COMMENT '总笔数',
	`success_amount` decimal(11,2) DEFAULT NULL COMMENT '成功金额',
  `success_rec_num` int(11) DEFAULT null COMMENT '成功笔数',
	`fail_amount` decimal(11,2) DEFAULT NULL COMMENT '失败金额',
  `fail_rec_num` int(11) DEFAULT null COMMENT '失败笔数',
  `sumtally` char(1) DEFAULT 0 COMMENT '是否汇总记账:1-是 0-否',
  `deal_status` char(1) DEFAULT 0 COMMENT '处理状态：0-未处理 1-已处理',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录修改时间',
  `create_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  `update_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_batch_no` (`batch_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '支付信息记录表';

-- 序号|企业端流水号|行内行外标志|收款清算行行号|收款账号|收款人姓名|金额|附言|备用字段1|备用字段2|备用字段3|
CREATE TABLE `t_s_pay_record_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ccode` VARCHAR(32) DEFAULT NULL COMMENT 'ccode',
  `parent_code` VARCHAR(32) DEFAULT NULL COMMENT 'parent_code',
  `order_num` int(6) DEFAULT NULL COMMENT '序号:1-6位数字',
  `service_id` int(11) DEFAULT NULL COMMENT '业务id',
  `flow_no` VARCHAR(20) DEFAULT NULL COMMENT '企业端流水号',
  `pay_type` CHAR(1) DEFAULT NULL COMMENT '行内外标志：1行内、2跨行（必填）',
  `clean_bank_no` VARCHAR(20) DEFAULT NULL COMMENT '收款清算行行号：最长12位数字（行内非必填）',
  `rece_acct_no` VARCHAR(32) DEFAULT NULL COMMENT '收款账号：最大长度32位数字（必填）',
  `rece_name` VARCHAR(255) DEFAULT NULL COMMENT '收款人姓名：最长支持60位字符，为账户户名（必填）',
  `amount` decimal(11,2) DEFAULT NULL COMMENT '金额',
  `summary` VARCHAR(255) DEFAULT NULL COMMENT '附言：144字符',
  `note1` VARCHAR(255) DEFAULT NULL COMMENT '备用1',
  `note2` VARCHAR(255) DEFAULT NULL COMMENT '备用2',
  `note3` VARCHAR(255) DEFAULT NULL COMMENT '备用3',
  `deal_status` char(1) DEFAULT 0 COMMENT '处理状态：0-未处理 1-交易成功 2-交易失败',
  `fail_code` VARCHAR(255) DEFAULT 0 COMMENT '失败代码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录修改时间',
  `create_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  `update_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_parent_code` (`parent_code`) USING BTREE,
  UNIQUE KEY `index_flow_no` (`flow_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '支付信息记录表详情表';


ALTER TABLE t_s_pay_record add trade_type int(5) COLLATE utf8_bin DEFAULT 0 COMMENT '0-佣金发放 1-提现';
ALTER TABLE t_s_pay_record_detail add trade_type int(5) COLLATE utf8_bin DEFAULT 0 COMMENT '0-佣金发放 1-提现';
ALTER TABLE t_d_bank_info add hfb_id int(5) COLLATE utf8_bin DEFAULT 0 COMMENT '汇付宝id';




CREATE TABLE `t_s_pay_record_hfb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ccode` VARCHAR(32) DEFAULT NULL COMMENT 'ccode',
  `batch_no` VARCHAR(20) DEFAULT NULL COMMENT '企业端批次号',
  `batch_amt` decimal(11,2) DEFAULT NULL COMMENT '总金额',
  `batch_num` int(11) DEFAULT null COMMENT '总笔数',
  `ext_param1` VARCHAR(50) DEFAULT NULL COMMENT '商户自定义原样返回，最大长度50个字符',
  `hy_bill_no` VARCHAR(255) DEFAULT NULL COMMENT '汇付宝交易号(订单号)',
  `summary` VARCHAR(255) DEFAULT NULL COMMENT '摘要',
  `note1` VARCHAR(255) DEFAULT NULL COMMENT '备用域1',
  `note2` VARCHAR(255) DEFAULT NULL COMMENT '备用域2',
  `note3` VARCHAR(255) DEFAULT NULL COMMENT '备用域3',
	`success_amount` decimal(11,2) DEFAULT NULL COMMENT '成功金额',
  `success_rec_num` int(11) DEFAULT null COMMENT '成功笔数',
  `deal_status` char(1) DEFAULT 0 COMMENT '处理状态：0-未处理 1-已处理',
	`trade_type` int(5) COLLATE utf8_bin DEFAULT 0 COMMENT '0-佣金发放 1-提现',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录修改时间',
  `create_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  `update_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_batch_no` (`batch_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '汇付宝支付信息记录表';

CREATE TABLE `t_s_pay_record_detail_hfb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ccode` VARCHAR(32) DEFAULT NULL COMMENT 'ccode',
  `parent_code` VARCHAR(32) DEFAULT NULL COMMENT 'parent_code',
  `service_id` int(11) DEFAULT NULL COMMENT '业务id',
  `flow_no` VARCHAR(20) DEFAULT NULL COMMENT '商户流水号',
  `bank_no` VARCHAR(20) DEFAULT NULL COMMENT '银行编号',
  `pay_type` CHAR(1) DEFAULT 0 COMMENT '支付类型标志：0-对私、1-对公（必填）',
  `rece_acct_no` VARCHAR(32) DEFAULT NULL COMMENT '收款账号：最大长度32位数字（必填）',
  `rece_name` VARCHAR(255) DEFAULT NULL COMMENT '收款人姓名：最长支持60位字符，为账户户名（必填）',
  `amount` decimal(11,2) DEFAULT NULL COMMENT '金额',
  `reason` VARCHAR(255) DEFAULT NULL COMMENT '付款理由：是需要去业务端设置的，字典固定值',
  `province` VARCHAR(255) DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(255) DEFAULT NULL COMMENT '城市',
  `rece_bank_info` VARCHAR(500) DEFAULT NULL COMMENT '收款支行信息',
  `summary` VARCHAR(255) DEFAULT NULL COMMENT '附言：144字符',
  `note1` VARCHAR(255) DEFAULT NULL COMMENT '备用1',
  `note2` VARCHAR(255) DEFAULT NULL COMMENT '备用2',
  `note3` VARCHAR(255) DEFAULT NULL COMMENT '备用3',
  `deal_status` char(1) DEFAULT 0 COMMENT '处理状态：0-未处理 1-交易成功 2-交易失败',
  `trade_type` int(5) COLLATE utf8_bin DEFAULT 0 COMMENT '0-佣金发放 1-提现',
  `fail_code` VARCHAR(255) DEFAULT 0 COMMENT '失败代码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '该条记录修改时间',
  `create_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  `update_user` VARCHAR(32) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_parent_code` (`parent_code`) USING BTREE,
  UNIQUE KEY `index_flow_no` (`flow_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '支付信息记录表详情表';


-- select * from t_d_return_error ORDER BY id desc;
-- select COUNT(0) from t_d_return_error ;
--  ORDER BY id desc limit 10;
INSERT INTO `ziniutest`.`t_s_system_conf` (`id`, `type`, `type_name`, `set_value`, `is_use`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES ('3', '3', '是否开启自动付款扫描', '1', '1', '2020-06-17 19:48:18', '2020-06-19 11:35:26', NULL, '1be68530676911e98b6a57441203b364');
INSERT INTO `ziniutest`.`t_s_system_conf` (`id`, `type`, `type_name`, `set_value`, `is_use`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES ('2', '2', '最大限额', '10000', '1', '2020-06-17 19:47:49', '2020-06-17 19:47:49', NULL, NULL);
INSERT INTO `ziniutest`.`t_s_system_conf` (`id`, `type`, `type_name`, `set_value`, `is_use`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES ('1', '1', '最小限额', '100', '1', '2020-06-17 19:47:28', '2020-06-19 14:12:06', NULL, '1be68530676911e98b6a57441203b364');

