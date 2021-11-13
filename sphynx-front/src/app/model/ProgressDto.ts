export class ProgressDto {
  readonly currentValue: number;
  readonly totalValue: number;

  constructor(currentValue: number, totalValue: number) {
    this.currentValue = currentValue;
    this.totalValue = totalValue;
  }

  getPercentage() {
    return this.totalValue > 0 ? this.currentValue * 100 / this.totalValue : 0;
  }
}
