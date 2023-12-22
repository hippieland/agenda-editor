import React, { useState } from 'react';

const AgendaForm = ({ onSubmit }) => {
  const [agendaName, setAgendaName] = useState('');
  const [creditable, setCreditable] = useState(false);
  const [content, setContent] = useState('');
  const [duration, setDuration] = useState('');
  const [validationError, setValidationError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!agendaName.trim()) {
      setValidationError('Agenda Name is required.');
      return;
    }

    if (creditable && (!content.trim() || !duration.trim())) {
      setValidationError('Content and Duration are required when Creditable is set to Yes.');
      return;
    }

    setValidationError('');

    const newAgenda = {
      name: agendaName,
      creditable: creditable,
      content: content,
      duration: duration,
    };

    onSubmit(newAgenda);

    setAgendaName('');
    setCreditable(false);
    setContent('');
    setDuration('');
  };

  return (
    <form onSubmit={handleSubmit}>
      {/* ... existing code ... */}
      <label>
        Agenda Name:
        <input type="text" value={agendaName} onChange={(e) => setAgendaName(e.target.value)} />
      </label>

      <label>
        Creditable:
        <input
          type="checkbox"
          checked={creditable}
          onChange={(e) => setCreditable(e.target.checked)}
        />
      </label>

      {creditable && (
        <div>
          <label>
            Content:
            <input type="text" value={content} onChange={(e) => setContent(e.target.value)} />
          </label>

          <label>
            Duration (min):
            <input
              type="text"
              value={duration}
              onChange={(e) => setDuration(e.target.value)}
            />
          </label>
        </div>
      )}

      {validationError && <div style={{ color: 'red' }}>{validationError}</div>}

      <button type="submit">Create Agenda</button>
    </form>
  );
};

export default AgendaForm;
