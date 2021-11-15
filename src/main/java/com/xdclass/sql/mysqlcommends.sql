

CREATE TABLE `case` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `app` varchar(128) DEFAULT NULL COMMENT '业务应用',
  `module` varchar(128) DEFAULT NULL COMMENT '模块',
  `title` varchar(128) DEFAULT NULL COMMENT '用例名称',
  `method` varchar(128) DEFAULT NULL COMMENT 'Http提交方法',
  `url` varchar(128) DEFAULT NULL COMMENT '接口',
  `run` varchar(32) DEFAULT NULL COMMENT '是否运行 yes/no',
  `headers` varchar(128) DEFAULT '{}' COMMENT '请求头',
  `pre_case_id` int(11) DEFAULT '-1' COMMENT '是否有前置用例id',
  `pre_fields` varchar(128) DEFAULT '[]' COMMENT '前置的字段, 获取请求结果的哪个字段，用于当前case的header还是body,双&name& 替代值',
  `request_body` varchar(128) DEFAULT '{}' COMMENT '请求内容,$XX用于替换',
  `expect_result` varchar(1024) DEFAULT NULL COMMENT '预期结果',
  `assert_type` varchar(64) DEFAULT NULL COMMENT '断言类型, 判断状态码、data内容或数组长度',
  `pass` varchar(64) DEFAULT NULL COMMENT '是否通过，yes, no',
  `msg` varchar(128) DEFAULT NULL COMMENT '测试用例额外描述新',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `response` text COMMENT '实际结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `case` (`id`, `app`, `module`, `title`, `method`, `url`, `run`, `headers`, `pre_case_id`, `pre_fields`, `request_body`, `expect_result`, `assert_type`, `pass`, `msg`, `update_time`, `response`)
VALUES
	(1,'小滴课堂','user','用户登录','post','/pub/api/v1/web/web_login','yes','{\"Content-Type\": \"application/x-www-form-urlencoded\"}',-1,'[]','{\"phone\": \"testnumber\", \"pwd\": \"1234567890\"}','0','code','True','模块:user, 标题:用户登录,断言类型:code,响应:None','2020-07-04 12:01:25',''),
	(4,'小滴课堂','order','用户订单列表','get','/user/api/v1/order/find_orders','yes','{\"token\":\"$token$\"}',1,'[{\"field\":\"token\",\"scope\":\"header\"}]','{}','0','data_json','True','模块:order, 标题:用户订单列表,断言类型:data_json,响应:None','2020-07-04 12:01:25',''),
	(5,'小滴课堂','video','首页视频卡片','get','/pub/api/v1/web/index_card','yes','{}',-1,'[]','{}','0','data_json_array','True','模块:video, 标题:首页视频卡片,断言类型:data_json_array,响应:None','2020-07-04 12:01:25',''),
	(6,'小滴课堂','user','用户个人信息','get','/pub/api/v1/web/user_info','yes','{\"token\":\"$token$\"}',1,'[{\"field\":\"token\",\"scope\":\"header\"}]','{}','0','data_json','True','模块:user, 标题:用户个人信息,断言类型:data_json,响应:None','2020-07-04 12:01:25',''),
	(7,'小滴课堂','favorate','新增收藏','post','/user/api/v1/favorite/save','yes','{\"token\":\"$token$\", \"Content-Type\": \"application/x-www-form-urlencoded\"}',1,'[{\"field\":\"token\",\"scope\":\"header\"}]','{\"video_id\":53}','0','code','True','模块:favorate, 标题:新增收藏,断言类型:code,响应:None','2020-07-04 12:01:25',''),
	(8,'小滴课堂','category','分类列表','get','/pub/api/v1/web/all_category','yes','{}',-1,'[]','{}','0','data_json_array','True','模块:category, 标题:分类列表,断言类型:data_json_array,响应:None','2020-07-04 12:01:26',''),
	(9,'小滴课堂','video','视频详情','get','/pub/api/v1/web/video_detail','yes','{}',-1,'[]','{\"video_id\":53}','0','data_json','True','模块:video, 标题:视频详情,断言类型:data_json,响应:None','2020-07-04 12:01:26',''),
	(10,'小滴课堂','favorate','我的收藏','get','/user/api/v1/favorite/page','yes','{\"token\":\"$token$\"}',1,'[{\"field\":\"token\",\"scope\":\"header\"}]','{}','0','data_json','True','模块:favorate, 标题:我的收藏,断言类型:data_json,响应:None','2020-07-04 12:01:26','');
