import React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'dva';
import { Card, Form, Row, Col, DatePicker, Select, Space, Button } from 'antd';
import { formatMessage } from 'umi-plugin-react/locale';
import { FormInstance } from 'antd/lib/form';
import moment from 'moment';
import { UserModelState } from '@/models/user';
import { TransQuery, MerSubItem } from './data';
import { StateType } from './model';

interface SearchProps {
  dispatch: Dispatch<any>;
  merNo?: string;
  query: TransQuery;
  merSubs: MerSubItem[];
  form: FormInstance;
}

const DateFormat: React.FC<Record<string, any>> = ({ value, format, onChange, ...rest }) => {
  return (
    <DatePicker {...rest}
      value={value && value !== '' ? moment(value as string, format) : null}
      onChange={date => onChange(date ? date.format(format) : '')}
    />
  )
}

const defaultColConfig = {
  lg: 8,
  md: 12,
  xxl: 8,
  xl: 8,
  sm: 12,
  xs: 24,
};

const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};
const tailLayout = {
  wrapperCol: { span: 24 },
};

const SearchView: React.FC<SearchProps> = props => {
  const { dispatch, form, merNo, query, merSubs } = props;

  const [termNos, setTermNos] = React.useState<string[]>([]);

  React.useEffect(() => {
    handleMerNoChange((merNo as string))
  }, [merNo]);

  const handleMerNoChange = (merNo: string) => {
    dispatch({
      type: 'trans/fetchTermNos',
      payload: merNo,
      callback: (termNos: string[]) => {
        setTermNos(termNos);
        form.setFieldsValue({
          ...form.getFieldsValue(),
          termNo: undefined,
        });
      }
    })
  }

  const handleSubmit = (values: any) => {
    dispatch({
      type: 'trans/fetchQuery',
      payload: {
        ...values,
        page: 0,
        size: query.size,
      },
    })
  }

  return (
    <Card style={{ marginBottom: '20px' }} bodyStyle={{ paddingBottom: 0 }}>
      <Form
        form={form}
        initialValues={{
          ...query,
          merNo,
        }}
        onFinish={handleSubmit}
      >
        <Row gutter={16} justify="start">
          <Col {...defaultColConfig} >
            <Form.Item
              name="merNo" {...layout}
              label={formatMessage({ id: 'trans.merNo.title' })}
              rules={[
                {
                  required: true,
                  message: formatMessage({ id: 'trans.merNo.required' })
                }
              ]}
            >
              <Select onChange={handleMerNoChange} placeholder={formatMessage({ id: 'trans.merNo.placeholder' })}>
                {merSubs?.map(sub => (
                  <Select.Option key={sub.merNo} value={sub.merNo}>{`${sub.merNo}[${sub.merName}]`}</Select.Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          <Col {...defaultColConfig} >
            <Form.Item {...layout} label={formatMessage({ id: 'trans.termNo.title' })} name="termNo">
              <Select placeholder={formatMessage({ id: 'trans.termNo.placeholder' })}>
                {termNos?.map(termNo => (
                  <Select.Option key={termNo} value={termNo}>{termNo}</Select.Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          <Col {...defaultColConfig} >
            <Form.Item {...layout} label={formatMessage({ id: 'trans.tranType.title' })} name="tranType">
              <Select placeholder={formatMessage({ id: 'trans.tranType.placeholder' })} allowClear={true}>
                <Select.Option value='PURCHASE'>{formatMessage({ id: 'trans.tranType.PURCHASE' })}</Select.Option>
                <Select.Option value='VOID'>{formatMessage({ id: 'trans.tranType.VOID' })}</Select.Option>
                <Select.Option value='REFUND'>{formatMessage({ id: 'trans.tranType.REFUND' })}</Select.Option>
                <Select.Option value='CONFIRM'>{formatMessage({ id: 'trans.tranType.CONFIRM' })}</Select.Option>
                <Select.Option value='OFFLINE'>{formatMessage({ id: 'trans.tranType.OFFLINE' })}</Select.Option>
                <Select.Option value='ADJUST'>{formatMessage({ id: 'trans.tranType.ADJUST' })}</Select.Option>
                <Select.Option value='TIPS'>{formatMessage({ id: 'trans.tranType.TIPS' })}</Select.Option>
                <Select.Option value='WITHDRAWAL'>{formatMessage({ id: 'trans.tranType.WITHDRAWAL' })}</Select.Option>
                <Select.Option value='PRE_AUTH'>{formatMessage({ id: 'trans.tranType.PRE_AUTH' })}</Select.Option>
                <Select.Option value='REVERSAL'>{formatMessage({ id: 'trans.tranType.REVERSAL' })}</Select.Option>
              </Select>
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={16} justify="start">
          <Col {...defaultColConfig} >
            <Form.Item {...layout} label={formatMessage({ id: 'trans.respCode.title' })} name="tranStatus">
              <Select placeholder={formatMessage({ id: 'trans.respCode.placeholder' })} allowClear={true}>
                <Select.Option value='0'>{formatMessage({ id: 'trans.respCode.success' })}</Select.Option>
                <Select.Option value='1'>{formatMessage({ id: 'trans.respCode.fail' })}</Select.Option>
              </Select>
            </Form.Item>
          </Col>
          <Col {...defaultColConfig} >
            <Form.Item
              {...layout} name="tranDate"
              label={formatMessage({ id: 'trans.tranDate.title' })}
            >
              <DateFormat style={{ width: '100%' }} showToday={false} allowClear={false} format="YYYYMMDD"
                placeholder={formatMessage({ id: 'trans.tranDate.placeholder' })}
                disabledDate={(date: moment.Moment) => date && (date >= moment().endOf('day') || date < moment().add('day', -30))}
              />
            </Form.Item>
          </Col>
          <Col {...defaultColConfig} style={{ textAlign: 'right' }}>
            <Form.Item {...tailLayout}>
              <Space>
                <Button type="primary" htmlType="submit">{formatMessage({ id: 'trans.query.submit' })}</Button>
                <Button onClick={() => form.resetFields()}>{formatMessage({ id: 'trans.query.reset' })}</Button>
              </Space>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </Card>
  );
}

export default connect(
  ({ user, trans }: {
    user: UserModelState;
    trans: StateType,
  }) => ({
    merNo: user.user.merNo,
    query: trans.query,
    merSubs: trans.merSubs,
  }),
)(SearchView);
