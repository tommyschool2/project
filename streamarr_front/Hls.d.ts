declare class Hls {
  static isSupported: () => boolean;
  static Events: any;

  attachMedia: (htmlElement: HTMLVideoElement) => void;
  on: (event: string, func: (...args: unknown[]) => unknown) => void;
  loadSource: (src: string) => void;
}
