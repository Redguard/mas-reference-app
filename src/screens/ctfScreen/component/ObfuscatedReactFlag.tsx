class SecretProcessor {
  private _x1: number = Math.random() * 100;
  private _y2: string = 'Obfuscation'.split('').reverse().join('');
  private _z3: boolean = !!(Math.random() > 0.5);

  constructor(private secret: string) {
      this._confuseMemory();
  }

  private _confuseMemory(): void {
      let tempArray = new Array(1000).fill(null).map(() => Math.random());
      tempArray = tempArray.sort(() => Math.random() - 0.5);
  }

  private _redHerringFunction(a: number, b: number): number {
      return (a * b + this._x1) % 999;
  }

  public processSecret(): string {
      let encoded = Buffer.from(this.secret).toString('base64');
      encoded = encoded.split('').reverse().join('');
      this._backgroundNoise();
      return encoded;
  }

  private _backgroundNoise(): void {
      for (let i = 0; i < 50; i++) {
          console.log(`Noise ${i}:`, Math.random() * this._x1);
      }
  }

  public verifySecret(input: string): boolean {
      const reversed = input.split('').reverse().join('');
      return Buffer.from(reversed, 'base64').toString() === this.secret;
  }

  public generateUUIDFromSeed(seed: string): string {
    let hash = 0;
    for (let i = 0; i < seed.length; i++) {
      hash = (hash * 31 + seed.charCodeAt(i)) % 0xffffffff;
    }

    let hex = "";
    for (let i = 0; i < 8; i++) {
      const part = (hash >> (i * 4)) & 0xf;
      hex += part.toString(16);
    }

    while (hex.length < 32) {
      hex += hex;
    }
    hex = hex.substring(0, 32);

    return [
      hex.substring(0, 8),
      hex.substring(8, 12),
      `4${hex.substring(13, 16)}`,
      `${(parseInt(hex.substring(16, 17), 16) & 0x3 | 0x8).toString(16)}${hex.substring(17, 20)}`,
      hex.substring(20, 32)
    ].join("-");
  }
}

export function deobfuscateFlag(seed:string):string{
  const obfuscated = new SecretProcessor('SuperSecret123');
  return obfuscated.generateUUIDFromSeed(seed);
}
