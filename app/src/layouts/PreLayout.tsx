import React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'dva';
import { Redirect } from 'umi';
import { PageLoading } from '@ant-design/pro-layout';
import ProLayout, { BasicLayoutProps as ProLayoutProps, Settings } from '@ant-design/pro-layout';
import { ConnectState } from '@/models/connect';
import RightContent from '@/components/GlobalHeader/RightContent';
import { User } from '@/models/user';
import BasicFooter from './BasicFooter';
import logo from '../assets/logo.png';

export interface LayoutProps extends ProLayoutProps {
  settings: Settings;
  dispatch: Dispatch<any>;
  loading: boolean | undefined;
  user: User;
}

const PreLayout: React.FC<LayoutProps> = props => {
  const { dispatch, settings, loading, user, children } = props;

  React.useEffect(() => {
    dispatch({ type: 'user/fetchUser' });
  }, []);

  if (!loading && user) {
    switch (user.status) {
      case "00": {
        return <Redirect to="/"></Redirect>;
      }
      default: {
        return (
          <ProLayout
            logo={logo}
            footerRender={() => <BasicFooter />}
            rightContentRender={() => <RightContent />}
            {...settings}
          >
            {children}
          </ProLayout>);
      }
    }
  } else {
    return <PageLoading />;
  }
};

export default connect(({ settings, user, loading }: ConnectState) => ({
  settings,
  user: user.user,
  loading: loading.models.user,
}))(PreLayout);
