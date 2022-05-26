import Head from "next/head";
import { ReactNode } from "react";

export type Prop = {
  children: ReactNode;
};

export function DefaultLayout({ children }: Prop) {
  return (
    <>
      <Head>
        <title>Streamarr</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      {children}
    </>
  );
}
