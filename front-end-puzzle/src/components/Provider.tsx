'use client';

import { Provider as ReduxProvider } from 'react-redux';
import { store } from '@/features/puzzle/store/store';

export default function Providers({
  children,
}: {
  children: React.ReactNode;
}) {
  return <ReduxProvider store={store}>{children}</ReduxProvider>;
}