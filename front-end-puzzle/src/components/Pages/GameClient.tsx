"use client";

import { usePlayback } from '../../hooks/usePlayback';
import Header from '../Header';
import MoveInfo from '../MoveInfo';
import PuzzleGrid from '../PuzzleGrid';
import Controls from '../Controls';
import SpeedControl from '../SpeedControl';
import ProgressBar from '../ProgressBar';
import MoveList from '../MoveList';
import StatesPreview from '../StatesPreview';
import Footer from '../Footer';

export default function App() {
  // Attiva il loop di autoplay
  usePlayback();

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 text-white flex flex-col items-center justify-start py-4 sm:py-6 px-3 sm:px-4 gap-4 sm:gap-6 overflow-y-auto">
      <Header />
      <MoveInfo />
      <PuzzleGrid />
      <Controls />
      <SpeedControl />
      <ProgressBar />
      <MoveList />
      <StatesPreview />
      <Footer />
    </div>
  );
}
