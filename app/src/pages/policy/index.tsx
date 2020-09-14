import React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'dva';
import { Checkbox, Radio, Button, Alert } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { FormattedMessage, formatMessage } from 'umi-plugin-react/locale';
import router from 'umi/router';
import { PolicyData } from './data.d';

interface PolicyProps {
  loading: boolean;
  policy: PolicyData;
  dispatch: Dispatch<any>;
}

const PolicyView: React.FC<PolicyProps> = props => {
  const { loading, policy, dispatch } = props;

  const [isAgree, setIsAgree] = React.useState<boolean>(false);

  React.useEffect(() => {
    dispatch({ type: 'policy/fetch' });
  }, []);

  const handleAgree = () => {
    dispatch({
      type: 'policy/fetchAgree',
      callback: () => {
        dispatch({
          type: 'user/setUser',
        });
        router.replace('/');
      }
    })
  }

  return (
    <PageHeaderWrapper pageHeaderRender={() => (<></>)} style={{ backgroundColor: 'white' }}>
      <br />
      <Alert
        message={formatMessage({ id: 'policy.alert.notice' })}
        description={formatMessage({ id: 'policy.alert.message' })}
        type="warning" showIcon closable
      />
      <br />
      <div style={{ textAlign: 'center' }}>
        <h1>{policy.title}</h1>
      </div>
      <div dangerouslySetInnerHTML={{ __html: policy.content }} />
      <br />
      <div style={{ textAlign: 'center' }}>
        <Checkbox checked={isAgree} onChange={() => setIsAgree(!isAgree)} style={{ fontSize: '20px', display: 'inline', fontWeight: 'bold' }}>
          <span><FormattedMessage id='policy.read.agree' /></span>
        </Checkbox>
        <Button type="primary" shape="round" size="large" disabled={!isAgree} onClick={handleAgree} loading={loading}>
          <span><FormattedMessage id='policy.option.agree' /></span>
        </Button>
      </div>
      <br />
    </PageHeaderWrapper>
  )
};

export default connect(
  ({
    policy,
    loading,
  }: {
    policy: PolicyData;
    loading: { models: { [key: string]: boolean } };
  }) => ({
    policy,
    loading: loading.models.policy,
  }),
)(PolicyView);
